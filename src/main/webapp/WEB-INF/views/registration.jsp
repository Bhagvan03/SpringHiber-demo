<%@include file="libs.jsp"%>
<html>
<head>
    <title>UserManagement</title>

    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCHOYCKxkXzolkCo8m65U9VvmfzOnrNX50&callback=initializeMap"></script>
    <script type="text/javascript" src="<c:url value='/resources/js/googlemap/jquery.min.js'/>" ></script>
    <%--<script type="text/javascript" src="<c:url value="/resources/js/alertify/alertify.js"/> "></script>
    <script type="text/javascript" src="<c:url value="/resources/js/alertify/alertify.js"/> "></script>
    <link rel="stylesheet" href="<c:url value='/resources/css/alertify/alertify.min.css'/>" />
    <link rel="stylesheet" href="<c:url value='/resources/css/alertify/alertify.rtl.min.css'/>" />
    <link rel="stylesheet" href="<c:url value='/resources/css/alertify/themes/bootstrap.min.css'/>" />
    <link rel="stylesheet" href="<c:url value='/resources/css/alertify/themes/bootstrap.rtl.min.css'/>" />
    <link rel="stylesheet" href="<c:url value='/resources/css/alertify/themes/default.min.css'/>" />
    <link rel="stylesheet" href="<c:url value='/resources/css/alertify/themes/default.rtl.min.css'/>" />
    <link rel="stylesheet" href="<c:url value='/resources/css/alertify/themes/semantic.min.css'/>" />
    <link rel="stylesheet" href="<c:url value='/resources/css/alertify/themes/semantic.rtl.min.css'/>" />--%>

    <!-- JavaScript -->
    <%--<script type="text/javascript" src="<c:url value='/resources/js/jquery/jquery-3.2.0.min.js'/>" ></script>--%>
    <script type="text/javascript" src="<c:url value='/resources/js/googlemap/jquery.easing.1.3.js'/>" ></script>
    <script type="text/javascript" src="<c:url value='/resources/js/googlemap/markerAnimate.js'/>" ></script>
    <script type="text/javascript" src="<c:url value='/resources/js/googlemap/MarkerWithGhost.js'/>" ></script>
    <%--<script type="text/javascript" src="<c:url value='/resources/js/googlemap/SlidingMarker.js'/>" ></script>--%>
    <%--<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>--%>
<body bgcolor="#fffaf0" id="initializeMap" onload="initializeMap()">
<h1>${message}</h1><br/>
<div>
    <springform:form action="createnewuser" method="post">
        <center>
            <table border="1" width="40%" cellpadding="5">
                <thead>
                <tr>
                    <th colspan="3">Create User</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>USERNAME : </td>
                    <td colspan="2"><input type="text" name="userName" id="userName" placeholder="UserName"/></td>
                </tr>
                <tr>
                    <td>EMAIL ADDRESS : </td>
                    <td colspan="2"><input type="text" name="email" id="email" placeholder="Email Address"/><br/></td>
                </tr>
                <tr>
                    <td>PERMANENT ADDRESS : </td>
                    <td colspan="2"><input type="text" name="address" id="address" readonly/></td>
                </tr>
                <tr>
                    <td>USER ROLE : </td>
                    <c:forEach items="${roleList}" var="roleList">
                        <c:if test="${roleList ne 'ROLE_ADMIN'}">
                            <td><input type="radio" name="role" id="role" value="${roleList.getRole()}"/>${roleList.getRole()}</td>
                        </c:if>
                    </c:forEach>
                </tr>
                <tr>
                    <td colspan="3" align="center"><input type="submit" value="Submit" id="submit"/></td>
                </tr>
                </tbody>
                <input type="hidden" name="${_csrf.parameterName}"
                       value="${_csrf.token}" />
            </table>
        </center>
    </springform:form>
</div>
<div id="map" style="width:100%;height:500px;"></div>
<script>

    var map;
    var marker;
    var myCenter = new google.maps.LatLng(32.520204, 34.937258);
    var geocoder = new google.maps.Geocoder();
    var infowindow = new google.maps.InfoWindow();
    MarkerWithGhost.initializeGlobally();
    function initializeMap() {
        var mapCanvas = document.getElementById("map");
        var mapOptions = {center: myCenter, zoom: 5};
        map = new google.maps.Map(mapCanvas, mapOptions);

        marker = new google.maps.Marker({
            map: map,
            position: myCenter,
            draggable: true
        });

        markerDragerOpertion();


        google.maps.event.addListener(map, 'click', function (event) {
            getCoordinationToAddress(event.latLng);
            marker.setPosition(event.latLng);
        });
    }

    function markerDragerOpertion() {
        geocoder.geocode({'latLng': myCenter }, function(results, status) {
            if (status == google.maps.GeocoderStatus.OK) {
                if (results[1]) {
                    $('#address').val(results[1].formatted_address);
                    infowindow.setContent(results[1].formatted_address);
                    infowindow.open(map, marker);
                }
            }
        });

        google.maps.event.addListener(marker, 'click', function() {
            geocoder.geocode({'latLng': marker.getPosition()}, function(results, status) {
                if (status == google.maps.GeocoderStatus.OK) {
                    if (results[1]) {
                        $('#address').val(results[1].formatted_address);
                        infowindow.setContent(results[1].formatted_address);
                        infowindow.open(map, marker);
                    }
                }
            });
        });

        google.maps.event.addListener(marker, 'dragend', function() {

            geocoder.geocode({'latLng': marker.getPosition()}, function(results, status) {
                if (status == google.maps.GeocoderStatus.OK) {
                    if (results[1]) {
                        $('#address').val(results[1].formatted_address);
                        infowindow.setContent(results[1].formatted_address);
                        infowindow.open(map, marker);
                    }
                }
            });
        });
    }

    function getCoordinationToAddress(location) {
//        alertify.alert("your address getting...");
        console.log("your address getting...");
        var latlng = {lat: parseFloat(location.lat()), lng: parseFloat(location.lng())};
        geocoder = new google.maps.Geocoder();
        geocoder.geocode({'latLng': latlng}, function (results, status) {
            if (status == google.maps.GeocoderStatus.OK) {
                if (results[1]) {
                    $("#address").val(results[1].formatted_address);
                    infowindow.setContent($("#address").val());
                    infowindow.open(map, marker);
                    console.log("address : "+$("#address").val());
                }
            }
        });
    }
</script>
</body>
</html>