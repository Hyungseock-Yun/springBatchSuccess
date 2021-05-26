var mobileCheck = function () {
    var userAgent = navigator.userAgent;
    alert('userAgent : ' + userAgent);
    if (userAgent == null) {
        return false;
    }
    var mobile1 = userAgent.match("iPhone|iPod|iPad|Android|Windows CE|BlackBerry|Symbian|Windows Phone|webOS|Opera Mini|Opera Mobi|POLARIS|IEMobile|lgtelecom|nokia|SonyEricsson");
    var mobile2 = userAgent.match("LG|SAMSUNG|Samsung");
    if (mobile1 || mobile2) {
        return true;
    } else {
        return false;
    }
}