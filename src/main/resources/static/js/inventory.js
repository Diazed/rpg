$(document).ready(function(){
    $(".secret").hide();

    $(".inventory").click(function(){
        $(this).next().show();
        $(this).next().fadeToggle("slow");
    });
});