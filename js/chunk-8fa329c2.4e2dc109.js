(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-8fa329c2"],{6549:function(t,e,n){"use strict";n.r(e);var r=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"default-margin"},[n("button",{attrs:{id:"copy-url-button"},on:{click:function(e){return t.copyURL()}}},[t._v("\n        "+t._s(t.translate("Get shareable link to the page"))+"\n    ")]),n("input",{class:{"background-dark":!t.urlCopied},attrs:{id:"current-location-input",type:"text"}}),n("br"),t.urlCopied?n("p",{staticClass:"green",staticStyle:{"text-align":"center"},attrs:{id:"url-copied-text"}},[t._v("\n        "+t._s(t.translate("Url copied to clipboard"))+"\n    ")]):t._e()])},a=[],i=(n("6b54"),n("a481"),n("7f7f"),n("cebc")),o=n("11d9"),c=n("60bf"),u=n("2f62"),l=n("d76c"),s={name:"CopyUrlButton",data:function(){return{urlCopied:!1}},computed:Object(i["a"])({},Object(u["b"])({itemView:function(t){return t.dictionary.itemView}})),watch:{$route:"onUrlChange"},methods:{onUrlChange:function(){this.urlCopied=!1;var t=document.getElementById("current-location-input");t.value=""},copyURL:function(){this.urlCopied=!0;var t=document.getElementById("current-location-input");t.value=this.getCurrentLocation(),t.select(),t.setSelectionRange(0,99999),document.execCommand("copy"),console.log("Url copied: "+t.value)},getCurrentLocation:function(){var t=o["a"].getCurrentLocation();if(l["a"].isItem(this.$route)){var e=this.itemView.category+"&"+this.itemView.name;t=t.toString().replace(this.$route.params.id,e.replace(/\s+/g,"_"))}return t},translate:function(t){return c["a"].translate(t)}}},d=s,p=(n("abf8"),n("2877")),f=Object(p["a"])(d,r,a,!1,null,"02b37536",null);e["default"]=f.exports},abf8:function(t,e,n){"use strict";var r=n("f058"),a=n.n(r);a.a},f058:function(t,e,n){}}]);
//# sourceMappingURL=chunk-8fa329c2.4e2dc109.js.map