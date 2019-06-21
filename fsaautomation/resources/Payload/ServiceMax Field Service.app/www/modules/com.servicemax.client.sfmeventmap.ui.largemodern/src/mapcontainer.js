const parentWindow = window.parent;

var SVMXMapIframe = SVMXMapIframe || {};

(function() {
  let mapApi;
  let directions;
  let markers;
  let infoBox;
  let currentMarker;

  parentWindow.iFrameEventCallBack = function(evt) {
    if (typeof google === 'undefined' || !mapApi || !evt) {
      return;
    }
    const method = evt.method;
    switch (method) {
      case 'resize':
        __resizeMap(evt);
        break;
      case 'changeBounds':
        __changeBoundsMap(evt);
        break;
      case 'addCurrentMarker':
        __addCurrentMarker(evt);
        break;
      case 'addMarker':
        __addMarker(evt);
        break;
      case 'geoCoder':
        __runGeoCoder(evt);
        break;
      case 'createDirections':
        __createDirections(evt);
        break;
      case 'directionsResponse':
        __directionsResponses(evt);
        break;
      case 'getRoute':
        __getRoute(evt);
        break;
      case 'routeHomeLocation':
        __routeHomeLocations(evt);
        break;
      case 'markersLength':
        __getMarkersLength(evt);
        break;
      case 'fitToMarkers':
        __fitToMarkers(evt);
        break;
      case 'sortMarkers':
        __sortMarkers(evt);
        break;
      case 'getWayPoints':
        __getWayPoints(evt);
        break;
      case 'googleStatusMsg':
        __getGoogleStatusMsg(evt);
        break;
      case 'clearEvents':
        __clearEvents(evt);
        break;
      default:
        break;
    }
  };

  __getGoogleStatusMsg = function(evt) {
    evt.handler && evt.handler(google.maps.DirectionsStatus.OK);
  };

  __resizeMap = function(evt) {
    google.maps.event.trigger(mapApi, 'resize');
  };

  __changeBoundsMap = function(evt) {
    google.maps.event.trigger(mapApi, 'bounds_changed');
  };

  __addCurrentMarker = function(evt) {
    currentMarker = new google.maps.Marker({
      map: mapApi,
      position: evt.position,
      icon: evt.icon,
    });
  };

  __addMarker = function(evt) {
    const marker = new google.maps.Marker({
      map: mapApi,
      position: evt.position,
      icon: evt.icon,
    });
    if (evt.type == 'addInfo') {
      this.__addInfo(marker, evt);
    }
    markers = markers || [];
    markers.push({
      marker,
      record: evt.eventRecord,
    });
  };

  __runGeoCoder = function(evt) {
    const geocoder = new google.maps.Geocoder();
    let input = {};
    if (evt.type == 'location' && evt.currentLocation) {
      input = {
        location: evt.currentLocation,
      };
    } else if (evt.type == 'address' && evt.address) {
      input = {
        address: evt.address,
      };
    }
    geocoder.geocode(input, (results, status) => {
      if (status == 'OVER_QUERY_LIMIT') {
        setTimeout(() => {
          __runGeoCoder(evt);
        }, 100);
      } else {
        evt.handler && evt.handler(results, status);
      }
    });
  };

  __createDirections = function(evt) {
    if (!directions) {
      directions = new google.maps.DirectionsRenderer({
        suppressMarkers: true,
      });
    }
    directions.setMap(mapApi);
  };

  __directionsResponses = function(evt) {
    directions && directions.setDirections(evt.response);
  };

  __getRoute = function(evt) {
    const options = {
      origin: evt.start,
      destination: evt.end,
      waypoints: evt.waypoints,
      optimizeWaypoints: false,
      travelMode: google.maps.TravelMode.DRIVING,
    };
    const directionsService = new google.maps.DirectionsService();
    directionsService.route(options, (response, status) => {
      evt.handler && evt.handler(response, status);
    });
  };

  __getMarkersLength = function(evt) {
    const markerLength = (markers && markers.length) || 0;
    evt.handler && evt.handler(markerLength);
  };

  __routeHomeLocations = function(evt) {
    if (markers) {
      const rec = [];
      const norec = [];

      for (let i = 0; i < markers.length; i++) {
        if (markers[i].record != null) {
          rec.push(markers[i]);
        } else {
          norec.push(markers[i]);
        }
      }

      rec.sort((a, b) => {
        const kA = new Date(a.record.data.start);
        const kB = new Date(b.record.data.start);

        if (kA < kB) return -1;

        if (kA > kB) return 1;

        return 0;
      });

      markers = [];
      norec.length > 0 && markers.push(norec[0]);

      for (let j = 0; j < rec.length; j++) {
        markers.push(rec[j]);
      }

      norec.length > 1 && markers.push(norec[1]);
    }
  };

  __fitToMarkers = function(evt) {
    if (markers) {
      const bounds = new google.maps.LatLngBounds();

      for (let i = 0; i < markers.length; i++) {
        bounds.extend(markers[i].marker.getPosition());
      }
      mapApi.fitBounds(bounds);
    }
  };

  __sortMarkers = function(evt) {
    if (markers) {
      markers.sort((a, b) => {
        const kA = new Date(a.record.data.start);
        const kB = new Date(b.record.data.start);

        if (kA < kB) return -1;

        if (kA > kB) return 1;

        return 0;
      });
    }
  };

  __getWayPoints = function(evt) {
    if (markers && markers.length > 1) {
      const end = markers[markers.length - 1].marker.getPosition();
      const start = markers[0].marker.getPosition();
      const waypoints = [];
      for (let idx = 1; idx < markers.length - 1; idx++) {
        waypoints.push({
          location: markers[idx].marker.getPosition(),
          stopover: true,
        });
      }
      evt.handler && evt.handler(start, end, waypoints);
    }
  };

  __addInfo = function(marker, evt) {
    marker.info = new infoBox({
      content: evt.content,
      boxClass: 'svmx-sfmevent-marker-popup',
      closeBoxURL: '',
      pixelOffset: new google.maps.Size(-175, -250),
    });

    google.maps.event.addListener(marker.info, 'domready', () => {
      const infoDom = marker.info.getDom();

      google.maps.event.addDomListener(
        infoDom.querySelector('#details-button'),
        'click',
        () => {
          const params = {
            type: 'details',
          };
          evt.handler && evt.handler(params);
          setTimeout(() => {
            marker.info.close();
          }, 500);
        }
      );

      google.maps.event.addDomListener(
        infoDom.querySelector('#get-directions-button'),
        'click',
        () => {
          const params = {
            type: 'direction',
            lat: marker.getPosition().lat(),
            lng: marker.getPosition().lng(),
          };
          evt.handler && evt.handler(params);
          setTimeout(() => {
            marker.info.close();
          }, 500);
        }
      );
    });

    google.maps.event.addListener(marker, 'click', () => {
      for (let i = 0; i < markers.length; i++) {
        if (
          markers[i] &&
          markers[i].marker &&
          markers[i].marker.info &&
          markers[i].marker != marker
        ) {
          markers[i].marker.info.close();
        }
      }

      if (marker.info.isOpen()) {
        marker.info.close();
      } else {
        marker.info.open(mapApi, marker);
      }
    });
  };

  __clearEvents = function(evt) {
    if (markers) {
      for (let i = 0; i < markers.length; i++) {
        markers[i].marker.setMap(null);
        google.maps.event.clearInstanceListeners(markers[i].marker);

        if (markers[i].marker.info) {
          markers[i].marker.info.close();
        }

        markers[i].marker = null;
      }

      if (directions) {
        directions.setMap(null);
        directions = null;
      }

      markers.length = 0;
    }
    currentMarker && currentMarker.setMap(null);
    currentMarker = null;
  };

  __createMap = function() {
    const mapOptions = {
      zoom: 2,
      minZoom: 2,
      center: new google.maps.LatLng(77.3, 0),
      fullscreenControl: false,
      mapTypeId: google.maps.MapTypeId.ROADMAP,
      gestureHandling: 'greedy',
    };
    const mpDiv = document.getElementById('map_display');
    mapApi = new google.maps.Map(mpDiv, mapOptions);
    infoBox = MapInfoBox.initialize();
    parentWindow.notifyMapLoaded();
  };

  SVMXMapIframe.initialize = function() {
    __createMap();
  };
}());
