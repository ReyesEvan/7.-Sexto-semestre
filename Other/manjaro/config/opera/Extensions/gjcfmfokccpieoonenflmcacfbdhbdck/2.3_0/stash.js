let data,s_=[],today,yesterday,week,monthStart,T1,T1L,T2,T2L,testId,ctxM_=null,srn_=null,showUnvisited_=null,mj_=['m_1','m_2','m_3','m_4','m_5','m_6','m_7','m_8','m_9','m_10','m_11','m_12'],lio,p_,url_=null,xCtx=135;const _m=()=>{if(p_.innerHTML='',_mD(),data.op9)for(let g=s_.length-1;0<=g;g--)_aN_L(s_[g],null);else for(let g=s_.length-1;0<=g;g--)_aN(s_[g],null);let c=document.createElement('div');c.className='tmpCl',p_.appendChild(c),br.textContent=s_.length,br.broj=s_.length,s_.length||_pB(),p_.style.display='block'},_mD=()=>{T1=document.createElement('div'),T1.id='tdNode',T1.className='vrClass',T1.style.display='none',p_.appendChild(T1),T1.textContent=chrome.i18n.getMessage('today'),T1L=document.createElement('div'),T1L.id='tdList',T1L.style.display='none',T1L.className='LClass',p_.appendChild(T1L),T2=document.createElement('div'),T2.id='ydNode',T2.style.display='none',T2.className='vrClass',p_.appendChild(T2),T2.textContent=chrome.i18n.getMessage('yesterday'),T2L=document.createElement('div'),T2L.id='ydList',T2L.style.display='none',T2L.className='LClass',p_.appendChild(T2L)};function _md(c){1===c.button&&c.preventDefault(),ctxM_&&ctxM_.blur(),this.style.transform='scale(0.97)',this.addEventListener('mouseleave',_ml),testId=this.id}function _mu(c){if(c.stopPropagation(),c.preventDefault(),this.style.transform='scale(1)',this.removeEventListener('mouseleave',_ml),this.id===testId)if(0===c.button)data.op8?data.op2&&c.metaKey?_tO(this.u,!1):data.op1?_tO(this.u,!0):chrome.tabs.update({url:this.u}):data.op2&&c.ctrlKey?_tO(this.u,!1):data.op1?_tO(this.u,!0):chrome.tabs.update({url:this.u});else if(1===c.button)_tO(this.u,!1);else if(2===c.button){ctxM_=document.createElement('div'),ctxM_.className='ctxM',ctxM_.u=this.u;let g=this.parentNode;g.style.backgroundColor='#BEC4E2';let n=document.createElement('div');n.className='ctxN',n.id='cn1',n.textContent=chrome.i18n.getMessage('ont'),ctxM_.appendChild(n);let q=document.createElement('div');q.className='ctxN',q.id='cn2',q.textContent=chrome.i18n.getMessage('del'),ctxM_.appendChild(q);let v=p_.clientWidth,w=c.pageX,A=c.pageY,B=window.innerHeight;w+xCtx>v&&(w=v-xCtx),A+68>B&&(A=B-68),ctxM_.style.top=A+'px',ctxM_.style.left=w+'px',ctxM_.style.display='block',document.body.appendChild(ctxM_),ctxM_.addEventListener('click',_cp),ctxM_.addEventListener('blur',_cb),ctxM_.setAttribute('tabIndex','1'),ctxM_.focus()}}function _ml(){this.style.transform='scale(1)',this.removeEventListener('mouseleave',_ml)}function _od(c){c.stopPropagation(),c.preventDefault(),this.style.backgroundColor='';let g=c.dataTransfer.getData('text/html'),n=document.createElement('div');n.innerHTML=g;let q=n.getElementsByTagName('img')[0];if(this.style.backgroundColor='',q&&q.src){let v=this.getElementsByClassName('imgPrv')[0],w=this.u;v.onerror=function(){v.src='../img/def.png'},v.onload=function(){if(100>this.naturalWidth){this.src='../img/def.png';let A=this.parentNode.getElementsByClassName('imgPrv_ico')[0];A.src=q.src,A.style.display='block'}},v.src=q.src;for(let A=s_.length-1;0<=A;A--)s_[A].u===w&&(s_[A].i=q.src);chrome.runtime.sendMessage({imgDrop:s_,url:w,img_:q.src})}}function _do(c){url_||(c.preventDefault(),this.style.backgroundColor='#BEC4E2')}function _dl(){this.style.backgroundColor=''}function _ds(){url_=this.u}function _de(c){url_&&c.pageX>window.innerWidth&&_tO(this.u,!!data.op7),url_=null,this.style.transform='scale(1)',this.removeEventListener('mouseleave',_ml)}function _cp(c){0===c.button&&('cn1'===c.target.id?(_tO(this.u,!1),this.blur()):'cn2'===c.target.id&&(_rN(this.u),_spl(this.u),this.blur()))}const _spl=c=>{let g;for(let n=s_.length-1;0<=n;n--)s_[n].u===c&&(g=s_.splice(n,1));chrome.runtime.sendMessage({delUrl:c,s:s_,d_n:g}),s_.length||_pB(),srn_&&(data.op9?srn_=document.getElementsByClassName('stBox_L'):srn_=document.getElementsByClassName('stBox'))};function _dc(c){0===c.button&&('d_d_02'===c.target.id?_delAll():'d_d_03'===c.target.id?_delVis():'d_d_04'===c.target.id?_showUV():'d_d_05'===c.target.id?chrome.runtime.sendMessage({exp:1}):'d_d_06'===c.target.id?chrome.runtime.openOptionsPage():'d_d_07'===c.target.id?chrome.tabs.create({url:'https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=4LFHZT6R4K6CA',active:!0}):'d_d_08'===c.target.id&&_view()),d_d.blur()}const _delAll=()=>{let c=confirm(chrome.i18n.getMessage('confirm1'));!0==c&&(s_=[],br.broj=0,br.textContent=0,p_.innerHTML='',_mD(),chrome.runtime.sendMessage({delAll:1}),_pB())},_delVis=()=>{let c=confirm(chrome.i18n.getMessage('confirm2')),g=0;if(!0==c){let n=[];for(let q=s_.length-1;0<=q;q--)s_[q].p&&(n.unshift(s_[q]),s_.splice(q,1),g=1);g&&(p_.style.display='none',chrome.runtime.sendMessage({delVis:s_,d_v:n}),_m())}},_showUV=()=>{let c,g=document.getElementsByClassName('LClass');c=data.op9?document.getElementsByClassName('stBox_L'):document.getElementsByClassName('stBox');for(let v=c.length-1;0<=v;v--)c[v].p&&(c[v].parentNode.style.display='none');for(let v=g.length-1;0<=v;v--)g[v].clientHeight||(g[v].style.display='none',g[v].previousSibling.style.display='none');showUnvisited_=1;let n=document.createElement('div');n.className='topB',top_h.appendChild(n);let q=document.createElement('div');q.className='topBack',q.textContent=chrome.i18n.getMessage('back'),n.appendChild(q),n.addEventListener('click',v=>{if(0===v.button){p_.style.display='none';for(let w=c.length-1;0<=w;w--)c[w].parentNode.style.display='block';for(let w=g.length-1;0<=w;w--)g[w].children.length&&(g[w].style.display='block',g[w].previousSibling.style.display='block');q.className+=' topItem_off',setTimeout(()=>{p_.style.display='block',n.className+=' topIBg_off'},100),setTimeout(()=>{top_h.removeChild(n)},500),showUnvisited_=0}})},_view=()=>{p_.style.display='none',data.op9?(data.op9=0,d_d_08.textContent=chrome.i18n.getMessage('dd8')):(data.op9=1,d_d_08.textContent=chrome.i18n.getMessage('dd9')),_m(),chrome.runtime.sendMessage({d_new:data,d_new_v:1})};function _cb(){let g,c=this.u;g=data.op9?document.getElementsByClassName('stBox_L'):document.getElementsByClassName('stBox'),this.parentNode.removeChild(this),ctxM_=null;for(let n=g.length-1;0<=n;n--)g[n].u===c&&(g[n].parentNode.style.backgroundColor='')}const _mB=()=>{d_d.className='menuDD menuU',setTimeout(()=>{d_d.style.display='none',d_d.className='menuDD menuD',d_d.o=0},300)},_aN=(c,g)=>{let n;if(c.v>today)T1.style.display='block',T1L.style.display='block',n=T1L;else if(c.v>yesterday)T2.style.display='block',T2L.style.display='block',n=T2L;else if(c.v>week){let F=document.getElementById('wList');if(F)n=F;else{let G=document.createElement('div');G.id='wNode',G.className='vrClass',p_.appendChild(G),G.textContent=chrome.i18n.getMessage('l7d');let H=document.createElement('div');H.id='wList',H.className='LClass',p_.appendChild(H),n=H}}else if(c.v>monthStart){let F=document.getElementById('mList');if(F)n=F;else{let G=document.createElement('div');G.id='mNode',G.className='vrClass',p_.appendChild(G),G.textContent=chrome.i18n.getMessage('early_t_m');let H=document.createElement('div');H.id='mList',H.className='LClass',p_.appendChild(H),n=H}}else{let F=new Date(c.v),G=F.getFullYear(),H=F.getMonth(),I=H+'_'+G,J=document.getElementById(I+'_L');if(J)n=J;else{let K=document.createElement('div');K.id=I,K.className='vrClass',p_.appendChild(K);let L=F.getMonth();K.textContent=chrome.i18n.getMessage(mj_[L])+' '+G;let M=document.createElement('div');M.id=I+'_L',M.className='LClass',p_.appendChild(M),n=M}}let q=new URL(c.u).hostname;q.startsWith('www.')&&(q=q.substring(4));let v=document.createElement('div');v.className='stNode',v.u=c.u;let w=document.createElement('div');w.u=c.u,w.id=c.v,w.p=c.p,w.t=c.t,w.className='stBox',v.appendChild(w);let A=document.createElement('img');A.className='imgPrv',w.appendChild(A);let B=document.createElement('img');B.className='imgPrv_ico',B.setAttribute('style','position: absolute;display: none;width: 24px;height: 24px;left: 83px;top:53px;z-index: 100;'),w.appendChild(B);let C=document.createElement('div');C.className='txtBox',w.appendChild(C);let D=document.createElement('div');D.className='ti_C',D.textContent=c.t,C.appendChild(D);let E=document.createElement('div');if(E.className='dom_C',E.textContent=q,C.appendChild(E),w.addEventListener('mousedown',_md),w.addEventListener('mouseup',_mu),w.addEventListener('drop',_od),w.addEventListener('dragover',_do),w.addEventListener('dragleave',_dl),g){p_.scrollTo(0,0),A.onerror=function(){A.src='../img/def.png'},A.onload=function(){100>this.naturalWidth&&(A.src='../img/def.png',B.src=c.i,B.style.display='block')},A.src=c.i,v.style.height='0px',n.insertBefore(v,n.firstChild);let F=v.clientHeight;v.style.height=F+'px',v.style.height='85px'}else A.setAttribute('data-src',c.i),A.src='../img/ph.png',n.appendChild(v),lio.observe(A);v.addEventListener('dragstart',_ds),v.addEventListener('dragend',_de)},_aN_L=(c,g)=>{let n;if(c.v>today)T1.style.display='block',T1L.style.display='block',n=T1L;else if(c.v>yesterday)T2.style.display='block',T2L.style.display='block',n=T2L;else if(c.v>week){let B=document.getElementById('wList');if(B)n=B;else{let C=document.createElement('div');C.id='wNode',C.className='vrClass',p_.appendChild(C),C.textContent=chrome.i18n.getMessage('l7d');let D=document.createElement('div');D.id='wList',D.className='LClass',p_.appendChild(D),n=D}}else if(c.v>monthStart){let B=document.getElementById('mList');if(B)n=B;else{let C=document.createElement('div');C.id='mNode',C.className='vrClass',p_.appendChild(C),C.textContent=chrome.i18n.getMessage('early_t_m');let D=document.createElement('div');D.id='mList',D.className='LClass',p_.appendChild(D),n=D}}else{let B=new Date(c.v),C=B.getFullYear(),D=B.getMonth(),E=D+'_'+C,F=document.getElementById(E+'_L');if(F)n=F;else{let G=document.createElement('div');G.id=E,G.className='vrClass',p_.appendChild(G);let H=B.getMonth();G.textContent=chrome.i18n.getMessage(mj_[H])+' '+C;let I=document.createElement('div');I.id=E+'_L',I.className='LClass',p_.appendChild(I),n=I}}let q=document.createElement('div');q.className='stNode_L',q.u=c.u;let v=document.createElement('div');v.u=c.u,v.id=c.v,v.p=c.p,v.t=c.t,v.className='stBox_L',q.appendChild(v);let w=document.createElement('div');w.className='icon',w.style.backgroundImage='url(chrome://favicon/'+c.u+')',v.appendChild(w);let A=document.createElement('div');if(A.className='txtBox_L',A.textContent=c.t,v.appendChild(A),v.addEventListener('mousedown',_md),v.addEventListener('mouseup',_mu),g){p_.scrollTo(0,0),q.style.height='0px',n.insertBefore(q,n.firstChild);let B=q.clientHeight;q.style.height=B+'px',q.style.height='30px'}else n.appendChild(q);q.addEventListener('dragstart',_ds),q.addEventListener('dragend',_de)},_rN=c=>{let g=data.op9?document.getElementsByClassName('stBox_L'):document.getElementsByClassName('stBox');for(let n=g.length-1;0<=n;n--)if(g[n].u===c){let q=g[n].parentNode,v=q.clientHeight,w=q.parentNode,A=w.previousSibling,B=A.clientHeight,C=0;q.style.height=v+'px',q.style.height=0,br.broj-=1,br.textContent=br.broj,0==w.children.length-1&&(A.style.height=B+'px',A.style.height=0,C=1),setTimeout(()=>{w.removeChild(q),C&&('tdList'!==w.id&&'ydList'!==w.id?(A.parentNode.removeChild(A),w.parentNode.removeChild(w)):(A.style.display='none',w.style.display='none',A.style.height='65px'))},300)}srn_&&(data.op9?srn_=document.getElementsByClassName('stBox_L'):srn_=document.getElementsByClassName('stBox'))},_ref=()=>{let c=document.getElementById('bg_ref');if(!c){let g=document.createElement('div');g.id='bg_ref',g.textContent=chrome.i18n.getMessage('ctf'),document.body.appendChild(g),g.addEventListener('click',()=>{window.location.reload()})}},_visitNode=c=>{let g=data.op9?document.getElementsByClassName('stBox_L'):document.getElementsByClassName('stBox');for(let n=g.length-1;0<=n;n--)g[n].u===c&&(g[n].p=1)},_pB=()=>{let c=document.createElement('div');c.id='panelBG',p_.appendChild(c)},_hcl=c=>{0===c.button&&('tabBtn'===c.target.id?chrome.tabs.create({url:'tabGrid.html',active:!0}):'searchBtn'===c.target.id?_mS():'menuBtn'===c.target.id&&!d_d.o&&(d_d.style.display='block',d_d.o=1,d_d.focus()))},_tO=(c,g)=>{data.op6?chrome.tabs.query({currentWindow:!0,active:!0},n=>{chrome.tabs.create({url:c,active:g,index:n[0].index+1})}):chrome.tabs.create({url:c,active:g})};function _mS(){let c=document.createElement('div');c.className='topB',top_h.appendChild(c);let g=document.createElement('input');g.type='search',g.placeholder=chrome.i18n.getMessage('sph'),g.className='hsb hsb_A',c.appendChild(g),g.addEventListener('input',_sr),g.addEventListener('keydown',_escSr),g.focus(),c.addEventListener('click',function(n){0===n.button&&n.target===this&&_cS(this,g)}),srn_=data.op9?document.getElementsByClassName('stBox_L'):document.getElementsByClassName('stBox')}function _sr(){var c=this.value.toLowerCase();if(1>this.value.length)for(var g=srn_.length-1;0<=g;g--){let q=srn_[g].parentNode;q.style.display='block',q.parentNode.style.display='block',q.parentNode.previousSibling.style.display='block'}else if(2<this.value.length){let q={};for(var g=srn_.length-1;0<=g;g--){let w=srn_[g].parentNode,A=srn_[g].t.toLowerCase()+' '+srn_[g].u.toLowerCase();A.includes(c)?(w.style.display='block',q[w.parentNode.id]=1):w.style.display='none'}let v=document.getElementsByClassName('LClass');for(var n=v.length-1;0<=n;n--)q[v[n].id]?(v[n].style.display='block',v[n].previousSibling.style.display='block'):(v[n].style.display='none',v[n].previousSibling.style.display='none')}}function _escSr(c){27===c.keyCode&&_cS(this.parentNode,this)}const _cS=(c,g)=>{p_.style.display='none',g.className='hsb hsb_B';let n,q=document.getElementsByClassName('LClass');n=data.op9?document.getElementsByClassName('stBox_L'):document.getElementsByClassName('stBox');for(let v=n.length-1;0<=v;v--)n[v].parentNode.style.display='block';for(let v=q.length-1;0<=v;v--)q[v].children.length?(q[v].style.display='block',q[v].previousSibling.style.display='block'):(q[v].style.display='none',q[v].previousSibling.style.display='none');setTimeout(()=>{p_.style.display='block',c.className+=' topIBg_off'},100),setTimeout(()=>{top_h.removeChild(c)},500),srn_=null};document.addEventListener('DOMContentLoaded',()=>{var c=new Date;today=new Date(c.getFullYear(),c.getMonth(),c.getDate()).getTime(),week=today-6.048e8,yesterday=today-8.64e7,monthStart=new Date(c.getFullYear(),c.getMonth()).getTime(),p_=document.getElementById('panel'),p_.addEventListener('scroll',()=>{ctxM_&&ctxM_.blur()}),document.documentElement.addEventListener('contextmenu',function(q){q.preventDefault()}),top_h.addEventListener('click',_hcl),d_d.o=0,d_d.addEventListener('blur',_mB),d_d.setAttribute('tabIndex','1'),d_d.addEventListener('click',_dc);let g=d_d.querySelectorAll('[data-i18n]');for(let q=g.length-1;0<=q;q--)g[q].textContent=chrome.i18n.getMessage(g[q].getAttribute('data-i18n'));lio=new IntersectionObserver(q=>{q.forEach(w=>{if(w.isIntersecting){let A=w.target;A.onerror=function(){A.src='../img/def.png'},A.onload=function(){if(100>this.naturalWidth){this.src='../img/def.png';let B=this.parentNode.getElementsByClassName('imgPrv_ico')[0];B.src=A.dataset.src,B.style.display='block'}},A.src=A.dataset.src,lio.unobserve(A)}})},{root:p_,rootMargin:'100px'}),chrome.storage.local.get(['s_','data'],q=>{s_=q.s_||[],data=q.data||[];let v=document.createElement('link');v.rel='stylesheet',v.type='text/css',data.op4?(v.href=chrome.extension.getURL('/stash_.css'),document.head.appendChild(v)):(v.href=chrome.extension.getURL('/stash.css'),document.head.appendChild(v)),setTimeout(()=>{top_h_bg.style.display='block',top_h.style.display='flex',_m(),data.op9&&(d_d_08.textContent=chrome.i18n.getMessage('dd9'))},200)});let n=chrome.i18n.getUILanguage();'hr'===n?xCtx=164:'de'===n?xCtx=160:'sr'===n&&(xCtx=178)}),chrome.runtime.onMessage.addListener(c=>{if(c.ukloni)s_=c.s,_rN(c.ukloni),s_.length||_pB();else if(c.dodaj){let q=new Date,v=new Date(q.getFullYear(),q.getMonth(),q.getDate()).getTime();if(!(v===today))_ref();else if(!srn_&&!showUnvisited_){s_=c.s;let w=document.getElementById('panelBG');w&&p_.removeChild(w),data.op9?_aN_L(c.dodaj,1):_aN(c.dodaj,1),br.textContent=s_.length,br.broj=s_.length}else _ref()}else if(c.vis)_visitNode(c.vis),s_=c.s;else if(c.oImp)p_.style.display='none',s_=c.oImp,setTimeout(_m,200);else if(c.delVis)_ref();else if(c.imgDrop){s_=c.imgDrop;let q=document.getElementsByClassName('stBox'),v=c.url,w=c.img_;for(let A=q.length-1;0<=A;A--)if(q[A].u===v){let B=q[A].getElementsByClassName('imgPrv')[0];B.onerror=function(){getI.src='../img/def.png'},B.onload=function(){this.prentNodegetElementsByClassName('imgPrv_ico')[0],ico&&this.removeChild(ico)},B.src=w,B.setAttribute('data-src',w)}}else c.delUrl?(s_=c.s,_rN(c.delUrl),s_.length||_pB()):c.delAll?(s_=[],p_.innerHTML='',_mD(),br.broj=0,br.textContent=0,_pB()):c.d_new?data=c.d_new:c.theme?(data.op4=2===c.theme?1:0,setTimeout(()=>{let q=document.getElementsByTagName('link')[0];q.href=data.op4?chrome.extension.getURL('/stash_.css'):chrome.extension.getURL('/stash.css')},200)):c.rf_&&window.location.reload()});