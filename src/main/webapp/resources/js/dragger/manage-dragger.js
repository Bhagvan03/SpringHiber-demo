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

/***
 * finding collision element(decider child element position)
 * @param childElement
 */
function collisionRectangle(parentElement,childElement) {
    var eleId,currentEleId,currentParentElement,status;
    eleId =$.trim($(childElement).attr('id'));
    $(parentElement).find('svg').each(function () {
        currentParentElement=$(this);
        currentEleId = $.trim(currentParentElement.attr('id'));
        if (eleId != currentEleId) {
            status = appendChildRectangle(currentParentElement, childElement);
            if (status) {
                makeChildElement(currentParentElement, childElement);
            }
        }
    })
}

/***
 * create child element into parent element
 * @param {currentParentElement,childElement}
 */

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
    if (!(horizontalParentPosition < elementTopPosition || currentElementTopPosition > horizontalChildPosition || verticalParentPosition < elementLeftPosition || currentElementLeftPosition > verticalChildPosition))
    {
        var newTop=(elementTopPosition-currentElementTopPosition)+'px';
        var newLeft=(elementLeftPosition-currentElementLeftPosition)+'px';
        childElement.css('top',newTop);
        childElement.css('left',newLeft);
        return true;
    }
    else {
        var left = childElement.offset().left+'px';
        var top = childElement.offset().top+'px';
        childElement.css('top',top);
        childElement.css('left',left);
        $("#container").append(childElement);
        return false;
    }

}


/**
 *make child & parent element
 * */
function makeChildElement(currentParentElement,childElement) {
    currentParentElement.append(childElement);
}
