// entry point of client side code
com_magenta_samara_eco_sliderjs_SliderServerComponent = function() {
    var connector = this;
    var element = connector.getElement();
    $(element).html("<div/>");
    $(element).css("padding", "5px 0px");

    var slider = $("div", element).slider({
        range: true,
        slide: function(event, ui) {
            connector.valueChanged(ui.values);
        }
    });

    connector.onStateChange = function() {
        var state = connector.getState();
        slider.slider("values", state.values);
        slider.slider("option", "min", state.minValue);
        slider.slider("option", "max", state.maxValue);
        $(element).width(state.width);
    }
}