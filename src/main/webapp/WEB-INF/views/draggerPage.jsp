<%@include file="libs.jsp"%>
<!doctype html>
<html lang="en">
<head>
    <title>Welcome Page</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>jQuery Draggable </title>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script type="text/javascript" src="<c:url value='/resources/js/jquery/jquery-3.2.0.min.js'/>" ></script>
    <script type="text/javascript" src="<c:url value='/resources/js/dragger/jquery.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/resources/js/dragger/jquery-ui.min.js'/>" ></script>
    <script>
        function drawDiv(){
            console.log("drawDiv");
            if(localStorage.getItem("leftPosition")!='' && localStorage.getItem("topPosition")){
               $("#draggable").css('top',localStorage.getItem("topPosition"));
               $("#draggable").css('left',localStorage.getItem("leftPosition"));
            }
          }

        $(document).ready(function() {
            init();
        } );

        function init() {
            $('#draggable').draggable( {
                cursor: 'move',
                containment: 'document',
                stop: getPositionOfDragger
            } );
        }

        function getPositionOfDragger() {
            console.log("getPositionOfDragger");
            var leftPosition,topPosition;
            leftPosition=$('#draggable').css( "left" );
            topPosition=$('#draggable').css("top");
//            console.log( "left1: " + $("#dropTarget").children("div").firstChild);

            localStorage.setItem("leftPosition",leftPosition);
            localStorage.setItem("topPosition",topPosition);
            console.log( "left: " + leftPosition + ", top: " + topPosition);
        }
    </script>
</head>
<body onload="drawDiv()">
<style>
    #draggable {
        height: 5%;
        /*left: 93px;*/
        position: relative;
        width: 14%;
        padding: 0.5em;
        text-align: center;
    }
    .left {
        background: orange none repeat scroll 0 0;
        border: 1px solid;
        float: left;
        height: 180px;
        text-align: center;
        width: 49%;
    }
</style>
<h1>${message}</h1><br>

<div>
    <div>
        <a href="login">Login</a><br/>
        <a href="registration">Registration</a><br/>
    </div>
    <div id="wrapper" style="height: 1000px;width: 800px">
        <div id="draggable" class="ui-widget-content">
            <p>Drag me around!</p>
        </div>
        <div id="dropTarget">
            <div class="left" ></div>
            <div class="left" ></div>
            <div class="left" ></div>
            <div class="left" ></div>
        </div>
    </div>
</div>
</body>
</html>
