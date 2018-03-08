<%@ include file="libs.jsp" %>
<!doctype html>
<html lang="en">
<head>
    <title>Welcome Page</title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script type="text/javascript" src="<c:url value='/resources/js/jquery/jquery-3.2.0.min.js'/>" ></script>
    <script type="text/javascript" src="<c:url value='/resources/js/dragger/jquery.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/resources/js/dragger/jquery-ui.min.js'/>" ></script>

    <script type="text/javascript" src="<c:url value='/resources/js/svg/dragsvg.js'/>" ></script>
    <%--<script type="text/javascript" src="<c:url value='/resources/js/dragger/manage-dragger.js'/>"></script>--%>
    <%--<script type="text/javascript" src="<c:url value='/resources/js/svg/raphael.js'/>" ></script>--%>
    <%--<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>--%>
    <%--<script src="//ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>--%>

    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>jQuery Draggable functionality</title>
    <style>
        #container {
            width:1000px;
            height:1000px;
            /*border: 10px solid transparent;*/
            background-color: #fcefa1;
            float: left;
        }
        .rectangle {
            border: 1px solid #FF0000;
            position: absolute;
        }
        .path {
            stroke-dasharray: 100;
            animation: dash 5s linear;
        }

        @keyframes dash {
            to {
                stroke-dashoffset: 1000;
            }
        }

        .element {
            height: 250px;
            width: 250px;
            margin: 0 auto;
            background-color: red;
            animation-name: stretch;
            animation-duration: 1.5s;
            animation-timing-function: ease-out;
            animation-delay: 0;
            animation-direction: alternate;
            animation-iteration-count: infinite;
            animation-fill-mode: none;
            animation-play-state: running;
        }

        @keyframes stretch {
            0% {
                transform: scale(.3);
                background-color: red;
                border-radius: 100%;
            }
            30% {
                background-color: blue;
            }
            50% {
                background-color: orange;
            }
            100% {
                transform: scale(1.5);
                background-color: yellow;
            }
        }

    </style>

    <script>
        var componentName,rectangleId=1;

        $(document).ready(function () {
            componentName=$('#componentType').find(":selected").val();
            $('#componentType').change(function () {
                componentName =  $.trim($(this).val());
            });
           var container=$("#container");
           init();
        });

        function init() {
            mouse = {
                x: 0,
                y: 0,
                startX: 0,
                startY: 0
            };

            var element = null;
            container.onmousemove = function (e) {
                setMousePosition(e);
                if (element !== null) {
                    element.style.width=Math.abs(mouse.x - mouse.startX) + 'px';
                    element.style.height=Math.abs(mouse.y - mouse.startY) + 'px';
                    element.style.left = (mouse.x - mouse.startX < 0) ? mouse.x + 'px' : mouse.startX + 'px';
                    element.style.top = (mouse.y - mouse.startY < 0) ? mouse.y + 'px' : mouse.startY + 'px';
                }
            }

            function setMousePosition(e) {
                container.style.cursor="default";
                var event = e || window.event; //Moz || IE
                var offset = $(event.target).offset();

                if (event.pageX) { //Moz
                    mouse.x=event.pageX;
                    mouse.y=event.pageY;
                }

                else if (event.pageY) { //IE
                    mouse.x=offset.left - $(window).scrollLeft();
                    mouse.y=offset.top - $(window).scrollTop();
                }
            }

            container.onclick = function (e) {
                if (element !== null) {
                    element = null;
                    $('.rectangle').draggable( {
                        cursor: 'move',
                        containment: 'document',
                        stop:function(event) {
                            collisionRectangle(container,$(event.target));
                        }
                    } );
                    $('.rectangle')
                        .resizable({
                            start: function(e, ui) {

                                },
                            resize: function(e, ui) {

                                },
                            stop: function(e, ui) {

                            }
                        });
                    container.style.cursor = "default";
//                    var currenElementId="rectangle-"+rectangleId;
//                    console.log("element : "+$("#"+currenElementId).attr('id'));
                    collisionRectangle(container,$("#rectangle-"+rectangleId));
                    console.log("draging finish.");
                    rectangleId++;
                } else {
                    console.log("begin.");
                    mouse.startX = mouse.x;
                    mouse.startY = mouse.y;
                    element = document.createElement('svg');
                    element.className = "rectangle";
                    element.id="rectangle-"+rectangleId;
                    element.style.left = mouse.x + 'px';
                    element.style.top = mouse.y + 'px';
                    container.appendChild(element);
                    container.style.cursor = "crosshair";
                }
            }
        }

        /**
         *make child & parent element
         * */
        function makeChildElement(currentParentElement,childElement) {
            currentParentElement.append(childElement);
            return true;
        }

        /***
         * finding collision element(decider child element position)
         * @param childElement
         */
        function collisionRectangle(parentElement,childElement) {
            var eleId,currentEleId,currentParentElement,status=false;
            var divElementArray=new Array();
            eleId =$.trim($(childElement).attr('id'));
            $(parentElement).find('svg').each(function () {
                currentParentElement=$(this);
                currentEleId = $.trim(currentParentElement.attr('id'));
                if (eleId != currentEleId) {
                    divElementArray.push(currentParentElement);
                }
            });

            divElementArray.reverse();
            for(var i=0;i < divElementArray.length; i++) {
                status = appendChildRectangle($(divElementArray[i]), childElement);
                if (status) {
                    status = makeChildElement($(divElementArray[i]), childElement);
                    break;
                }
            }
        }

        function appendChildRectangle(currentParentElement,childElement) {
            var elementWidth,elementHeight,elementLeftPosition,elementTopPosition;
            var currentElementWidth,currentElementHeight,currentElementLeftPosition,currentElementTopPosition;
            currentElementLeftPosition = currentParentElement.offset().left;
            currentElementTopPosition = currentParentElement.offset().top;
            currentElementHeight = currentParentElement.outerHeight(true);
            currentElementWidth = currentParentElement.outerWidth(true);
            var horizontalParentPosition = currentElementTopPosition + currentElementHeight;
            var verticalParentPosition = currentElementLeftPosition + currentElementWidth;
            elementLeftPosition = childElement.offset().left;
            elementTopPosition = childElement.offset().top;
            elementHeight = childElement.outerHeight(true);
            elementWidth = childElement.outerWidth(true);
            var horizontalChildPosition = elementTopPosition + elementHeight;
            var verticalChildPosition = elementLeftPosition + elementWidth;
                if (horizontalParentPosition < elementTopPosition || currentElementTopPosition > horizontalChildPosition || verticalParentPosition < elementLeftPosition || currentElementLeftPosition > verticalChildPosition)
                {
                    var left = childElement.offset().left+'px';
                    var top = childElement.offset().top+'px';
                    childElement.css('top',top);
                    childElement.css('left',left);
                    $("#container").append(childElement);
                    return false;
                }
                else {
                    var newTop=(elementTopPosition-currentElementTopPosition)+'px';
                    var newLeft=(elementLeftPosition-currentElementLeftPosition)+'px';
                    childElement.css('top',newTop);
                    childElement.css('left',newLeft);
                    return true;
                }

        }
    </script>

</head>
<body>
<h1>${message}</h1><br>
<div>
    <div>
        <a href="login">Login</a><br/>
        <a href="registration">Registration</a><br/>
        <a href="dragger">DragAndDrop</a><br/>
    </div>
    <div>
        <select id="componentType">
            <option value="rectangle" selected>Rectangle</option>
            <%--<option value="circle">Circle</option>--%>
            <%--<option value="line">Line</option>--%>
        </select>
    </div>
</div>
<div id="container"></div>
</body>
</html>