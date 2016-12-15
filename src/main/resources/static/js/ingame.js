$(document).ready(function(){
    $(".preitem").next().fadeOut(0);

    $(".preitem").mouseenter(function(){
        $(this).next().fadeIn("slow");
    });

    $(".preitem").mouseleave(function(){
        $(this).next().fadeOut(0);
    });
});