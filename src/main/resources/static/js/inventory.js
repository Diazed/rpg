$(document).ready(function(){
    $(".inventory").next().fadeOut(0);

    $(".inventory").click(function(){
        $(this).next().fadeToggle("slow");
    });
});