function distinguishTypes(type, s, url, w, h) {
    // var elm;
    console.log(":::: distinguishTypes start! ::::");
    console.log("distinguishTypes url : " + url);
    // elm = document.body.childNodes[document.body.childNodes.length - 1];
    // console.log("::::elm=" + elm.innerHTML);

    // var obj = {};

    switch (type) {
        case 'banner': case 'icover': default :
            console.log(":::: distinguishTypes 배너!!! ::::");
            document.getElementById('mobonDivBanner_' + s).innerHTML = "<iframe name='ifrad' id='mobonIframe_" + s + "' src='" + url + "' frameborder='0' scrolling='no' style='height:" + h + "px; width:" + w + "px;'></iframe>";
            // elm.insertAdjacentHTML("afterend", "<iframe name='ifrad' id='mobonIframe_" + s + "' src='" + url + "' frameborder='0' scrolling='no' style='height:" + h + "px; width:" + w + "px;'></iframe>");
            if ('icover' === type) {
                console.log(":::: distinguishTypes 아이커버!!! ::::");
                document.getElementById('mobonDivBanner_' + s).style.display = "none";
            }

            break;

        case 'floating': case 'mcover': case 'brandlink':
            console.log(":::: distinguishTypes 플로팅 or 커버!!! ::::");
            var banner = document.getElementById('mobonDivBanner_' + s);
            var script = document.createElement("script");
            script.src = url;
            banner.appendChild(script);
            break;
    }
}

function makeURL(url, values) {
    console.log(":::: makeURL start! ::::");
    var UAInfo = "&uaInfo=";
    var parse = "_";
    var platform = values.platform;
    var model = parse + values.model;
    var platformVersion = parse + values.platformVersion;
    var uaFullVersion = parse + values.uaFullVersion;
    var URL = url;
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
    URL = URL + UAInfo;

    console.log("platform : " + platform);
    console.log("model : " + model);
    console.log("platformVersion : " + platformVersion);
    console.log("uaFullVersion : " + uaFullVersion);
    console.log("isMobile : " + isMobile);
    console.log("brand : " + brand);
    console.log("Encoded URL : " + URL);
    console.log(":::: makeURL End! ::::");

    return URL;
}

async function makeAD(MobonADMakerObject) {
    try {
        console.log(":::: makeAD start! ::::");

        var type = MobonADMakerObject.type;
        var s = MobonADMakerObject.scriptNumber;
        var w = MobonADMakerObject.width;
        var h = MobonADMakerObject.height;
        var url = MobonADMakerObject.url;

        console.log("type : " + type);
        console.log("s : " + s);
        console.log("w : " + w);
        console.log("h : " + h);
        console.log("url : " + url);

        var secureChk = window.isSecureContext;
        var secureParam = "&secureChk=" + secureChk;

        if (secureChk === true) {
            var highEntropyValues = await navigator.userAgentData.getHighEntropyValues(["platform", "platformVersion", "architecture", "model", "uaFullVersion"]);
            url = makeURL(url, highEntropyValues);
            distinguishTypes(type, s, url + secureParam, w, h);
        } else {
            console.log("userAgent : " + navigator.userAgent);
            console.log("secureParam : " + secureParam);
            distinguishTypes(type, s, url + secureParam, w, h);
        }
    } catch (error) {
        console.log("error MSG : " + error);
        secureParam = "&secureChk=false";
        distinguishTypes(type, s, url + secureParam, w, h);
    }
}