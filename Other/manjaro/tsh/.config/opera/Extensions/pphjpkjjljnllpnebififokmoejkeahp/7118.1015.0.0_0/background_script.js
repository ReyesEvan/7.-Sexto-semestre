'use strict';db("mr.TestProvider");var ix,Hw,jx=db("mr.Init"),kx=function(a){void 0!==a&&jx.info("Using the "+(a?"Views (Harmony)":"WebUI")+" dialog.")};tp().init();ix=new sb("MediaRouter.Provider.WakeDuration");Hw=new Ww;
var lx=(new Promise(function(a,b){switch(window.location.host){case "enhhojjnijigcajfphajepfemndkmdlo":a();break;case "pphjpkjjljnllpnebififokmoejkeahp":chrome.management.get("enhhojjnijigcajfphajepfemndkmdlo",function(c){chrome.runtime.lastError||!c.enabled?a():b(Error("Dev extension is enabled"))});break;default:b(Error("Unknown extension id"))}})).then(function(){return chrome.mojoPrivate&&chrome.mojoPrivate.requireAsync?new Promise(function(a){chrome.mojoPrivate.requireAsync("media_router_bindings").then(function(b){mojo=b.getMojoExports&&
b.getMojoExports();b.start().then(function(c){a({mrService:b,mrInstanceId:c.instance_id||c,mrConfig:c.config})})})}):Promise.reject(Error("No mojo service loaded"))}).then(function(a){if(!a.mrService)throw Error("Failed to get MR service");var b=a.mrInstanceId;if(!b)throw Error("Failed to get MR instance ID.");jx.info("MR instance ID: "+b);kx(a.mrConfig.use_views_dialog);var c=a.mrService;if(!Hw)throw Error("providerManager not initialized.");c.setHandlers(Hw);hj(b)&&(ix.f="MediaRouter.Provider.FirstWakeDuration");
chrome.runtime.onSuspend.addListener(ix.b.bind(ix));lj(b);wp();b=Gw();window.addEventListener("unhandledrejection",function(a){a=a.reason;a.stack||(a=Error(a));jx.error("Unhandled promise rejection.",a)});Hw.Ya(c,b,a.mrConfig)}).then(void 0,function(a){jx.F(a.message);throw a;});[].concat(m([Fw(),Cw()].concat(m(Im()),m([qt(),mt()])))).forEach(function(a){fj(a)});Fw().addListener();Cw().addListener();chrome.runtime.onStartup.addListener(function(){});lx.then(void 0,function(){return window.close()});
