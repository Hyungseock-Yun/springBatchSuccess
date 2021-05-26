
function MobonAD(paramObject) {
    var mobon = Mobon
    mobon.makeAD(paramObject);}
 
var Mobon = {
    aDMakerObject : {},
    highEntropyValues: {},

    init: function(paramObject) {
        console.log('init~~~~~~~~');

        this.aDMakerObject = {
            type : paramObject.type,
            scriptNumber : paramObject.scriptNumber,
            width : paramObject.width,
            height : paramObject.height,
            bntype : paramObject.bntype,
            cntad : paramObject.cntad,
            cntsr : paramObject.cntsr,
            logoChk : paramObject.logoChk,
            iwh : paramObject.width + '_' + paramObject.height,
            mChk : paramObject.height,
            secureChk : window.isSecureContext,
            url : ''
        };

        this.aDMakerObject.url = "//www.mediacategory.com/servlet/adbnMobileBanner?from=" + escape(document.referrer);
        this.aDMakerObject.url+= "&s=" + this.aDMakerObject.scriptNumber;
        this.aDMakerObject.url+= "&bntype=" + this.aDMakerObject.bntype;
        this.aDMakerObject.url+= "&iwh=" + this.aDMakerObject.iwh;
        this.aDMakerObject.url+= "&mChk=" + this.aDMakerObject.mChk;
        this.aDMakerObject.url+= "&cntad=" + this.aDMakerObject.cntad;
        this.aDMakerObject.url+= "&cntsr=" + this.aDMakerObject.cntsr;
        this.aDMakerObject.url+= "&logoChk=" + this.aDMakerObject.logoChk;
        this.aDMakerObject.url+= "&secureChk=" + window.isSecureContext;

        console.log("this.aDMakerObject.url : " + this.aDMakerObject.url);

        var urlQuery='';
        var keys = Object.keys(this.aDMakerObject);
        for(var i=0; i<keys.length; i++) {
            console.log("key : " + keys[i] + ", value : " + this.aDMakerObject[keys[i]]);
            urlQuery+= keys[i]+'='+this.aDMakerObject[keys[i]];
            if(i != keys.length-1)
                urlQuery+='&'
        }

        console.log("urlQuery : " + urlQuery);




    },

    makeAD: async function(paramObject) {
        try {
            console.log(":::: makeAD start! ::::");

            //객체 init
            this.init(paramObject);

            if (this.aDMakerObject.secureChk === true) {

                this.highEntropyValues = await navigator.userAgentData.getHighEntropyValues(["platform", "platformVersion", "architecture", "model", "uaFullVersion"]);

                //realUrl 생성
                this.makeURL();

                //draw UI
                this.distinguishTypes();
            } else {
                console.log("userAgent : " + navigator.userAgent);
                console.log("secureParam : " + secureParam);
                this.distinguishTypes();
            }
        } catch (error) {
            console.log("error MSG : " + error);
            this.distinguishTypes();
        }
    },

    makeURL: function() {

       var url = this.aDMakerObject.url;
        console.log(":::: makeURL start! ::::");

        var UAInfo = "&uaInfo=";
        var parse = "_";
        var platform = this.highEntropyValues.platform;
        var model = parse + this.highEntropyValues.model;
        var platformVersion = parse + this.highEntropyValues.platformVersion;
        var uaFullVersion = parse + this.highEntropyValues.uaFullVersion;
        var URL = this.aDMakerObject.url;
        var isMobile = parse + navigator.userAgentData.mobile;
        var brands = navigator.userAgentData.brands;
        var brand = "";
        if (brands != null) {
            for (var i=0; i < brands.length; i++) {
                brand = brands[i].brand + "," + brand;
            }
            // brands.forEach(each => brand = each.brand + "," + brand)
        }
        brand = parse + brand.replaceAll(" ", "");
        console.log("UA param : " + UAInfo + platform + model + platformVersion + uaFullVersion + isMobile + brand);
        UAInfo = UAInfo + btoa(platform + model + platformVersion + uaFullVersion + isMobile + brand);
        URL += UAInfo;

        console.log("platform : " + platform);
        console.log("model : " + model);
        console.log("platformVersion : " + platformVersion);
        console.log("uaFullVersion : " + uaFullVersion);
        console.log("isMobile : " + isMobile);
        console.log("brand : " + brand);
        console.log("Encoded URL : " + URL);
        console.log(":::: makeURL End! ::::");

        this.aDMakerObject.url = URL;
    },

    distinguishTypes : function() {

        var type = this.aDMakerObject.type;
        var s = this.aDMakerObject.scriptNumber;
        var h = this.aDMakerObject.height;
        var w = this.aDMakerObject.width;
        var url = this.aDMakerObject.url;
        var myNode;// = document.getElementsByTagName("script")[document.getElementsByTagName("script").length-1];

        //TODO 그려준 스크립트와 신규로 그린 스크립트 분리 작업 필요
        for(var i=document.getElementsByTagName("script").length-1; i>= 0; i--) {
            var chNode = document.getElementsByTagName("script")[i];
            var ckNodeHtml = document.getElementsByTagName("script")[i].innerHTML;
            if( ckNodeHtml.includes('MobonAD(') ){
                myNode = document.getElementsByTagName("script")[i];
                chNode.setAttribute('field', 'drown')
                console.log('ckNodeHtml : ' + ckNodeHtml);
                break;
            }
        }

        // console.log("myNode.innerHTML---------------" + myNode.innerHTML);
        switch (type) {
            case 'banner': case 'icover': default :
                console.log(":::: distinguishTypes 배너!!! ::::");
                myNode.insertAdjacentHTML("afterend", "<iframe name='ifrad' id='mobonIframe_" + s + "' src='" + url + "' frameborder='0' scrolling='no' style='height:" + h + "px; width:" + w + "px;'></iframe>");
                if ('icover' === type) {
                    console.log(":::: distinguishTypes 아이커버!!! ::::");
                    document.getElementById('mobonDivBanner_' + s).style.display = "none";
                }

                break;

            case 'floating': case 'mcover': case 'brandlink':
                console.log(":::: distinguishTypes 플로팅 or 커버!!! ::::");
                var script = document.createElement("script");
                script.src = url;
                myNode.appendChild(script);
                break;
        }
    }
};



