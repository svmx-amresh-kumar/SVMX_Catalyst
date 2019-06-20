window.NREUM || (NREUM = {}), (__nr_require = (function(t, n, e) {
  function r(e) {
    if (!n[e]) {
      const o = (n[e] = { exports: {} });
      t[e][0].call(
        o.exports,
        n => {
          const o = t[e][1][n];
          return r(o || n);
        },
        o,
        o.exports
      );
    }
    return n[e].exports;
  }
  if (typeof __nr_require === 'function') return __nr_require;
  for (let o = 0; o < e.length; o++) r(e[o]);
  return r;
}(
  {
    1: [
      function(t, n, e) {
        function r(t) {
          try {
            s.console && console.log(t);
          } catch (n) {}
        }
        var o,
          i = t('ee'),
          a = t(15),
          s = {};
        try {
          (o = localStorage.getItem('__nr_flags').split(',')), console &&
            typeof console.log === 'function' &&
            (
              (s.console = !0),
              o.indexOf('dev') !== -1 && (s.dev = !0),
              o.indexOf('nr_dev') !== -1 && (s.nrDev = !0)
            );
        } catch (c) {}
        s.nrDev &&
          i.on('internal-error', t => {
            r(t.stack);
          }), s.dev &&
          i.on('fn-err', (t, n, e) => {
            r(e.stack);
          }), s.dev &&
          (
            r('NR AGENT IN DEVELOPMENT MODE'),
            r(`flags: ${a(s, (t, n) => t).join(', ')}`)
          );
      },
      {},
    ],
    2: [
      function(t, n, e) {
        function r(t, n, e, r, s) {
          try {
            p ? (p -= 1) : o(s || new UncaughtException(t, n, e), !0);
          } catch (f) {
            try {
              i('ierr', [f, c.now(), !0]);
            } catch (d) {}
          }
          return typeof u === 'function' && u.apply(this, a(arguments));
        }
        function UncaughtException(t, n, e) {
          (this.message =
            t ||
            'Uncaught error with no additional information'), (this.sourceURL = n), (this.line = e);
        }
        function o(t, n) {
          const e = n ? null : c.now();
          i('err', [t, e]);
        }
        var i = t('handle'),
          a = t(16),
          s = t('ee'),
          c = t('loader'),
          f = t('gos'),
          u = window.onerror,
          d = !1,
          l = 'nr@seenError',
          p = 0;
        (c.features.err = !0), t(1), (window.onerror = r);
        try {
          throw new Error();
        } catch (h) {
          'stack' in h &&
            (
              t(8),
              t(7),
              'addEventListener' in window && t(5),
              c.xhrWrappable && t(9),
              (d = !0)
            );
        }
        s.on('fn-start', (t, n, e) => {
          d && (p += 1);
        }), s.on('fn-err', function(t, n, e) {
          d && !e[l] && (f(e, l, () => !0), (this.thrown = !0), o(e));
        }), s.on('fn-end', function() {
          d && !this.thrown && p > 0 && (p -= 1);
        }), s.on('internal-error', t => {
          i('ierr', [t, c.now(), !0]);
        });
      },
      {},
    ],
    3: [
      function(t, n, e) {
        t('loader').features.ins = !0;
      },
      {},
    ],
    4: [
      function(t, n, e) {
        function r(t) {}
        if (
          window.performance &&
          window.performance.timing &&
          window.performance.getEntriesByType
        ) {
          let o = t('ee'),
            i = t('handle'),
            a = t(8),
            s = t(7),
            c = 'learResourceTimings',
            f = 'addEventListener',
            u = 'resourcetimingbufferfull',
            d = 'bstResource',
            l = 'resource',
            p = '-start',
            h = '-end',
            m = `fn${p}`,
            w = `fn${h}`,
            v = 'bstTimer',
            y = 'pushState',
            g = t('loader');
          (g.features.stn = !0), t(6);
          const b = NREUM.o.EV;
          o.on(m, function(t, n) {
            const e = t[0];
            e instanceof b && (this.bstStart = g.now());
          }), o.on(w, function(t, n) {
            const e = t[0];
            e instanceof b && i('bst', [e, n, this.bstStart, g.now()]);
          }), a.on(m, function(t, n, e) {
            (this.bstStart = g.now()), (this.bstType = e);
          }), a.on(w, function(t, n) {
            i(v, [n, this.bstStart, g.now(), this.bstType]);
          }), s.on(m, function() {
            this.bstStart = g.now();
          }), s.on(w, function(t, n) {
            i(v, [n, this.bstStart, g.now(), 'requestAnimationFrame']);
          }), o.on(y + p, function(t) {
            (this.time = g.now()), (this.startPath = location.pathname + location.hash);
          }), o.on(y + h, function(t) {
            i('bstHist', [
              location.pathname + location.hash,
              this.startPath,
              this.time,
            ]);
          }), f in window.performance &&
            (window.performance[`c${c}`]
              ? window.performance[f](
                u,
                t => {
                  i(d, [
                    window.performance.getEntriesByType(l),
                  ]), window.performance[`c${c}`]();
                },
                !1
              )
              : window.performance[f](
                `webkit${u}`,
                t => {
                  i(d, [
                    window.performance.getEntriesByType(l),
                  ]), window.performance[`webkitC${c}`]();
                },
                !1
              )), document[f]('scroll', r, { passive: !0 }), document[f](
            'keypress',
            r,
            !1
          ), document[f]('click', r, !1);
        }
      },
      {},
    ],
    5: [
      function(t, n, e) {
        function r(t) {
          for (var n = t; n && !n.hasOwnProperty(u);) {
            n = Object.getPrototypeOf(n);
          }
          n && o(n);
        }
        function o(t) {
          s.inPlace(t, [u, d], '-', i);
        }
        function i(t, n) {
          return t[1];
        }
        var a = t('ee').get('events'),
          s = t(18)(a, !0),
          c = t('gos'),
          f = XMLHttpRequest,
          u = 'addEventListener',
          d = 'removeEventListener';
        (n.exports = a), 'getPrototypeOf' in Object
          ? (r(document), r(window), r(f.prototype))
          : f.prototype.hasOwnProperty(u) &&
            (o(window), o(f.prototype)), a.on(`${u}-start`, function(t, n) {
          let e = t[1],
            r = c(e, 'nr@wrapped', () => {
              function t() {
                if (typeof e.handleEvent === 'function') {
                  return e.handleEvent(...arguments);
                }
              }
              const n = { object: t, function: e }[typeof e];
              return n ? s(n, 'fn-', null, n.name || 'anonymous') : e;
            });
          this.wrapped = t[1] = r;
        }), a.on(`${d}-start`, function(t) {
          t[1] = this.wrapped || t[1];
        });
      },
      {},
    ],
    6: [
      function(t, n, e) {
        let r = t('ee').get('history'),
          o = t(18)(r);
        (n.exports = r), o.inPlace(
          window.history,
          ['pushState', 'replaceState'],
          '-'
        );
      },
      {},
    ],
    7: [
      function(t, n, e) {
        let r = t('ee').get('raf'),
          o = t(18)(r),
          i = 'equestAnimationFrame';
        (n.exports = r), o.inPlace(
          window,
          [`r${i}`, `mozR${i}`, `webkitR${i}`, `msR${i}`],
          'raf-'
        ), r.on('raf-start', t => {
          t[0] = o(t[0], 'fn-');
        });
      },
      {},
    ],
    8: [
      function(t, n, e) {
        function r(t, n, e) {
          t[0] = a(t[0], 'fn-', null, e);
        }
        function o(t, n, e) {
          (this.method = e), (this.timerDuration = isNaN(t[1])
            ? 0
            : +t[1]), (t[0] = a(t[0], 'fn-', this, e));
        }
        var i = t('ee').get('timer'),
          a = t(18)(i),
          s = 'setTimeout',
          c = 'setInterval',
          f = 'clearTimeout',
          u = '-start',
          d = '-';
        (n.exports = i), a.inPlace(
          window,
          [s, 'setImmediate'],
          s + d
        ), a.inPlace(window, [c], c + d), a.inPlace(
          window,
          [f, 'clearImmediate'],
          f + d
        ), i.on(c + u, r), i.on(s + u, o);
      },
      {},
    ],
    9: [
      function(t, n, e) {
        function r(t, n) {
          d.inPlace(n, ['onreadystatechange'], 'fn-', s);
        }
        function o() {
          let t = this,
            n = u.context(t);
          t.readyState > 3 &&
            !n.resolved &&
            ((n.resolved = !0), u.emit('xhr-resolved', [], t)), d.inPlace(
            t,
            y,
            'fn-',
            s
          );
        }
        function i(t) {
          g.push(t), h && (x ? x.then(a) : w ? w(a) : ((E = -E), (O.data = E)));
        }
        function a() {
          for (let t = 0; t < g.length; t++) r([], g[t]);
          g.length && (g = []);
        }
        function s(t, n) {
          return n;
        }
        function c(t, n) {
          for (const e in t) n[e] = t[e];
          return n;
        }
        t(5);
        var f = t('ee'),
          u = f.get('xhr'),
          d = t(18)(u),
          l = NREUM.o,
          p = l.XHR,
          h = l.MO,
          m = l.PR,
          w = l.SI,
          v = 'readystatechange',
          y = [
            'onload',
            'onerror',
            'onabort',
            'onloadstart',
            'onloadend',
            'onprogress',
            'ontimeout',
          ],
          g = [];
        n.exports = u;
        const b = (window.XMLHttpRequest = function(t) {
          const n = new p(t);
          try {
            u.emit('new-xhr', [n], n), n.addEventListener(v, o, !1);
          } catch (e) {
            try {
              u.emit('internal-error', [e]);
            } catch (r) {}
          }
          return n;
        });
        if (
          (
            c(p, b),
            (b.prototype = p.prototype),
            d.inPlace(b.prototype, ['open', 'send'], '-xhr-', s),
            u.on('send-xhr-start', (t, n) => {
              r(t, n), i(n);
            }),
            u.on('open-xhr-start', r),
            h
          )
        ) {
          var x = m && m.resolve();
          if (!w && !m) {
            var E = 1,
              O = document.createTextNode(E);
            new h(a).observe(O, { characterData: !0 });
          }
        } else {
          f.on('fn-end', t => {
            (t[0] && t[0].type === v) || a();
          });
        }
      },
      {},
    ],
    10: [
      function(t, n, e) {
        function r(t) {
          let n = this.params,
            e = this.metrics;
          if (!this.ended) {
            this.ended = !0;
            for (let r = 0; r < d; r++) {
              t.removeEventListener(u[r], this.listener, !1);
            }
            if (!n.aborted) {
              if (
                ((e.duration = a.now() - this.startTime), t.readyState === 4)
              ) {
                n.status = t.status;
                const i = o(t, this.lastSize);
                if ((i && (e.rxSize = i), this.sameOrigin)) {
                  const c = t.getResponseHeader('X-NewRelic-App-Data');
                  c && (n.cat = c.split(', ').pop());
                }
              } else n.status = 0;
              (e.cbTime = this.cbTime), f.emit('xhr-done', [t], t), s('xhr', [
                n,
                e,
                this.startTime,
              ]);
            }
          }
        }
        function o(t, n) {
          const e = t.responseType;
          if (e === 'json' && n !== null) return n;
          const r =
            e === 'arraybuffer' || e === 'blob' || e === 'json'
              ? t.response
              : t.responseText;
          return h(r);
        }
        function i(t, n) {
          let e = c(n),
            r = t.params;
          (r.host = `${e.hostname}:${e.port}`), (r.pathname =
            e.pathname), (t.sameOrigin = e.sameOrigin);
        }
        var a = t('loader');
        if (a.xhrWrappable) {
          var s = t('handle'),
            c = t(11),
            f = t('ee'),
            u = ['load', 'error', 'abort', 'timeout'],
            d = u.length,
            l = t('id'),
            p = t(14),
            h = t(13),
            m = window.XMLHttpRequest;
          (a.features.xhr = !0), t(9), f.on('new-xhr', function(t) {
            const n = this;
            (n.totalCbs = 0), (n.called = 0), (n.cbTime = 0), (n.end = r), (n.ended = !1), (n.xhrGuids = {}), (n.lastSize = null), (p &&
              (p > 34 || p < 10)) ||
              window.opera ||
              t.addEventListener(
                'progress',
                t => {
                  n.lastSize = t.loaded;
                },
                !1
              );
          }), f.on('open-xhr-start', function(t) {
            (this.params = {
              method: t[0],
            }), i(this, t[1]), (this.metrics = {});
          }), f.on('open-xhr-end', function(t, n) {
            'loader_config' in NREUM &&
              'xpid' in NREUM.loader_config &&
              this.sameOrigin &&
              n.setRequestHeader('X-NewRelic-ID', NREUM.loader_config.xpid);
          }), f.on('send-xhr-start', function(t, n) {
            let e = this.metrics,
              r = t[0],
              o = this;
            if (e && r) {
              const i = h(r);
              i && (e.txSize = i);
            }
            (this.startTime = a.now()), (this.listener = function(t) {
              try {
                t.type === 'abort' && (o.params.aborted = !0), (t.type !==
                  'load' ||
                  (o.called === o.totalCbs &&
                    (o.onloadCalled || typeof n.onload !== 'function'))) &&
                  o.end(n);
              } catch (e) {
                try {
                  f.emit('internal-error', [e]);
                } catch (r) {}
              }
            });
            for (let s = 0; s < d; s++) {
              n.addEventListener(u[s], this.listener, !1);
            }
          }), f.on('xhr-cb-time', function(t, n, e) {
            (this.cbTime += t), n ? (this.onloadCalled = !0) : (this.called += 1), this.called !== this.totalCbs || (!this.onloadCalled && typeof e.onload === 'function') || this.end(e);
          }), f.on('xhr-load-added', function(t, n) {
            const e = `${l(t)}${!!n}`;
            this.xhrGuids &&
              !this.xhrGuids[e] &&
              ((this.xhrGuids[e] = !0), (this.totalCbs += 1));
          }), f.on('xhr-load-removed', function(t, n) {
            const e = `${l(t)}${!!n}`;
            this.xhrGuids &&
              this.xhrGuids[e] &&
              (delete this.xhrGuids[e], (this.totalCbs -= 1));
          }), f.on('addEventListener-end', (t, n) => {
            n instanceof m &&
              t[0] === 'load' &&
              f.emit('xhr-load-added', [t[1], t[2]], n);
          }), f.on('removeEventListener-end', (t, n) => {
            n instanceof m &&
              t[0] === 'load' &&
              f.emit('xhr-load-removed', [t[1], t[2]], n);
          }), f.on('fn-start', function(t, n, e) {
            n instanceof m &&
              (
                e === 'onload' && (this.onload = !0),
                ((t[0] && t[0].type) === 'load' || this.onload) &&
                  (this.xhrCbStart = a.now())
              );
          }), f.on('fn-end', function(t, n) {
            this.xhrCbStart &&
              f.emit(
                'xhr-cb-time',
                [a.now() - this.xhrCbStart, this.onload, n],
                n
              );
          });
        }
      },
      {},
    ],
    11: [
      function(t, n, e) {
        n.exports = function(t) {
          let n = document.createElement('a'),
            e = window.location,
            r = {};
          (n.href = t), (r.port = n.port);
          const o = n.href.split('://');
          !r.port &&
            o[1] &&
            (r.port = o[1]
              .split('/')[0]
              .split('@')
              .pop()
              .split(':')[1]), (r.port && r.port !== '0') ||
            (r.port = o[0] === 'https' ? '443' : '80'), (r.hostname =
            n.hostname || e.hostname), (r.pathname = n.pathname), (r.protocol =
            o[0]), r.pathname.charAt(0) !== '/' &&
            (r.pathname = `/${r.pathname}`);
          let i =
              !n.protocol || n.protocol === ':' || n.protocol === e.protocol,
            a = n.hostname === document.domain && n.port === e.port;
          return (r.sameOrigin = i && (!n.hostname || a)), r;
        };
      },
      {},
    ],
    12: [
      function(t, n, e) {
        function r() {}
        function o(t, n, e) {
          return function() {
            return i(t, [f.now()].concat(s(arguments)), n ? null : this, e), n
              ? void 0
              : this;
          };
        }
        var i = t('handle'),
          a = t(15),
          s = t(16),
          c = t('ee').get('tracer'),
          f = t('loader'),
          u = NREUM;
        typeof window.newrelic === 'undefined' && (newrelic = u);
        let d = [
            'setPageViewName',
            'setCustomAttribute',
            'setErrorHandler',
            'finished',
            'addToTrace',
            'inlineHit',
            'addRelease',
          ],
          l = 'api-',
          p = `${l}ixn-`;
        a(d, (t, n) => {
          u[n] = o(l + n, !0, 'api');
        }), (u.addPageAction = o(
          `${l}addPageAction`,
          !0
        )), (u.setCurrentRouteName = o(
          `${l}routeName`,
          !0
        )), (n.exports = newrelic), (u.interaction = function() {
          return new r().get();
        });
        const h = (r.prototype = {
          createTracer(t, n) {
            let e = {},
              r = this,
              o = typeof n === 'function';
            return i(`${p}tracer`, [f.now(), t, e], r), function() {
              if (
                (c.emit(`${o ? '' : 'no-'}fn-start`, [f.now(), r, o], e), o)
              ) {
                try {
                  return n.apply(this, arguments);
                } catch (t) {
                  throw (c.emit('fn-err', [arguments, this, t], e), t);
                } finally {
                  c.emit('fn-end', [f.now()], e);
                }
              }
            };
          },
        });
        a(
          'setName,setAttribute,save,ignore,onEnd,getContext,end,get'.split(
            ','
          ),
          (t, n) => {
            h[n] = o(p + n);
          }
        ), (newrelic.noticeError = function(t) {
          typeof t === 'string' && (t = new Error(t)), i('err', [t, f.now()]);
        });
      },
      {},
    ],
    13: [
      function(t, n, e) {
        n.exports = function(t) {
          if (typeof t === 'string' && t.length) return t.length;
          if (typeof t === 'object') {
            if (
              typeof ArrayBuffer !== 'undefined' &&
              t instanceof ArrayBuffer &&
              t.byteLength
            ) {
              return t.byteLength;
            }
            if (typeof Blob !== 'undefined' && t instanceof Blob && t.size) {
              return t.size;
            }
            if (!(typeof FormData !== 'undefined' && t instanceof FormData)) {
              try {
                return JSON.stringify(t).length;
              } catch (n) {}
            }
          }
        };
      },
      {},
    ],
    14: [
      function(t, n, e) {
        let r = 0,
          o = navigator.userAgent.match(/Firefox[\/\s](\d+\.\d+)/);
        o && (r = +o[1]), (n.exports = r);
      },
      {},
    ],
    15: [
      function(t, n, e) {
        function r(t, n) {
          let e = [],
            r = '',
            i = 0;
          for (r in t) o.call(t, r) && ((e[i] = n(r, t[r])), (i += 1));
          return e;
        }
        var o = Object.prototype.hasOwnProperty;
        n.exports = r;
      },
      {},
    ],
    16: [
      function(t, n, e) {
        function r(t, n, e) {
          n || (n = 0), typeof e === 'undefined' && (e = t ? t.length : 0);
          for (
            var r = -1, o = e - n || 0, i = Array(o < 0 ? 0 : o);
            ++r < o;

          ) {
            i[r] = t[n + r];
          }
          return i;
        }
        n.exports = r;
      },
      {},
    ],
    17: [
      function(t, n, e) {
        n.exports = {
          exists:
            typeof window.performance !== 'undefined' &&
            window.performance.timing &&
            typeof window.performance.timing.navigationStart !== 'undefined',
        };
      },
      {},
    ],
    18: [
      function(t, n, e) {
        function r(t) {
          return !(t && t instanceof Function && t.apply && !t[a]);
        }
        var o = t('ee'),
          i = t(16),
          a = 'nr@original',
          s = Object.prototype.hasOwnProperty,
          c = !1;
        n.exports = function(t, n) {
          function e(t, n, e, o) {
            function nrWrapper() {
              let r,
                a,
                s,
                c;
              try {
                (a = this), (r = i(arguments)), (s =
                  typeof e === 'function' ? e(r, a) : e || {});
              } catch (f) {
                l([f, '', [r, a, o], s]);
              }
              u(`${n}start`, [r, a, o], s);
              try {
                return (c = t.apply(a, r));
              } catch (d) {
                throw (u(`${n}err`, [r, a, d], s), d);
              } finally {
                u(`${n}end`, [r, a, c], s);
              }
            }
            return r(t)
              ? t
              : (n || (n = ''), (nrWrapper[a] = t), d(t, nrWrapper), nrWrapper);
          }
          function f(t, n, o, i) {
            o || (o = '');
            let a,
              s,
              c,
              f = o.charAt(0) === '-';
            for (c = 0; c < n.length; c++) {
              (s = n[c]), (a = t[s]), r(a) ||
                (t[s] = e(a, f ? s + o : o, i, s));
            }
          }
          function u(e, r, o) {
            if (!c || n) {
              const i = c;
              c = !0;
              try {
                t.emit(e, r, o, n);
              } catch (a) {
                l([a, e, r, o]);
              }
              c = i;
            }
          }
          function d(t, n) {
            if (Object.defineProperty && Object.keys) {
              try {
                const e = Object.keys(t);
                return e.forEach(e => {
                  Object.defineProperty(n, e, {
                    get() {
                      return t[e];
                    },
                    set(n) {
                      return (t[e] = n), n;
                    },
                  });
                }), n;
              } catch (r) {
                l([r]);
              }
            }
            for (const o in t) s.call(t, o) && (n[o] = t[o]);
            return n;
          }
          function l(n) {
            try {
              t.emit('internal-error', n);
            } catch (e) {}
          }
          return t || (t = o), (e.inPlace = f), (e.flag = a), e;
        };
      },
      {},
    ],
    ee: [
      function(t, n, e) {
        function r() {}
        function o(t) {
          function n(t) {
            return t && t instanceof r ? t : t ? c(t, s, i) : i();
          }
          function e(e, r, o, i) {
            if (!l.aborted || i) {
              t && t(e, r, o);
              for (var a = n(o), s = h(e), c = s.length, f = 0; f < c; f++) {
                s[f].apply(a, r);
              }
              const d = u[y[e]];
              return d && d.push([g, e, r, a]), a;
            }
          }
          function p(t, n) {
            v[t] = h(t).concat(n);
          }
          function h(t) {
            return v[t] || [];
          }
          function m(t) {
            return (d[t] = d[t] || o(e));
          }
          function w(t, n) {
            f(t, (t, e) => {
              (n = n || 'feature'), (y[e] = n), n in u || (u[n] = []);
            });
          }
          var v = {},
            y = {},
            g = {
              on: p,
              emit: e,
              get: m,
              listeners: h,
              context: n,
              buffer: w,
              abort: a,
              aborted: !1,
            };
          return g;
        }
        function i() {
          return new r();
        }
        function a() {
          (u.api || u.feature) && ((l.aborted = !0), (u = l.backlog = {}));
        }
        var s = 'nr@context',
          c = t('gos'),
          f = t(15),
          u = {},
          d = {},
          l = (n.exports = o());
        l.backlog = u;
      },
      {},
    ],
    gos: [
      function(t, n, e) {
        function r(t, n, e) {
          if (o.call(t, n)) return t[n];
          const r = e();
          if (Object.defineProperty && Object.keys) {
            try {
              return Object.defineProperty(t, n, {
                value: r,
                writable: !0,
                enumerable: !1,
              }), r;
            } catch (i) {}
          }
          return (t[n] = r), r;
        }
        var o = Object.prototype.hasOwnProperty;
        n.exports = r;
      },
      {},
    ],
    handle: [
      function(t, n, e) {
        function r(t, n, e, r) {
          o.buffer([t], r), o.emit(t, n, e);
        }
        var o = t('ee').get('handle');
        (n.exports = r), (r.ee = o);
      },
      {},
    ],
    id: [
      function(t, n, e) {
        function r(t) {
          const n = typeof t;
          return !t || (n !== 'object' && n !== 'function')
            ? -1
            : t === window ? 0 : a(t, i, () => o++);
        }
        var o = 1,
          i = 'nr@id',
          a = t('gos');
        n.exports = r;
      },
      {},
    ],
    loader: [
      function(t, n, e) {
        function r() {
          if (!x++) {
            let t = (b.info = NREUM.info),
              n = l.getElementsByTagName('script')[0];
            if (
              (
                setTimeout(u.abort, 3e4),
                !(t && t.licenseKey && t.applicationID && n)
              )
            ) {
              return u.abort();
            }
            f(y, (n, e) => {
              t[n] || (t[n] = e);
            }), c('mark', ['onload', a() + b.offset], null, 'api');
            const e = l.createElement('script');
            (e.src = `https://${t.agent}`), n.parentNode.insertBefore(e, n);
          }
        }
        function o() {
          l.readyState === 'complete' && i();
        }
        function i() {
          c('mark', ['domContent', a() + b.offset], null, 'api');
        }
        function a() {
          return E.exists && performance.now
            ? Math.round(performance.now())
            : (s = Math.max(new Date().getTime(), s)) - b.offset;
        }
        var s = new Date().getTime(),
          c = t('handle'),
          f = t(15),
          u = t('ee'),
          d = window,
          l = d.document,
          p = 'addEventListener',
          h = 'attachEvent',
          m = d.XMLHttpRequest,
          w = m && m.prototype;
        NREUM.o = {
          ST: setTimeout,
          SI: d.setImmediate,
          CT: clearTimeout,
          XHR: m,
          REQ: d.Request,
          EV: d.Event,
          PR: d.Promise,
          MO: d.MutationObserver,
        };
        var v = `${location}`,
          y = {
            beacon: 'bam.nr-data.net',
            errorBeacon: 'bam.nr-data.net',
            agent: 'js-agent.newrelic.com/nr-1071.min.js',
          },
          g = m && w && w[p] && !/CriOS/.test(navigator.userAgent),
          b = (n.exports = {
            offset: s,
            now: a,
            origin: v,
            features: {},
            xhrWrappable: g,
          });
        t(12), l[p]
          ? (l[p]('DOMContentLoaded', i, !1), d[p]('load', r, !1))
          : (l[h]('onreadystatechange', o), d[h]('onload', r)), c(
          'mark',
          ['firstbyte', s],
          null,
          'api'
        );
        var x = 0,
          E = t(17);
      },
      {},
    ],
  },
  {},
  ['loader', 2, 10, 4, 3]
)));
NREUM.info = {
  beacon: 'bam.nr-data.net',
  errorBeacon: 'bam.nr-data.net',
  licenseKey: 'eaf13636d6',
  applicationID: '194825820',
  sa: 1,
};
