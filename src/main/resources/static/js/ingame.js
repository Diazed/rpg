$(document).ready(function(){
    $(".preitem").parent().parent().next().fadeOut(0);

    $(".preitem").mouseenter(function(){
        $(this).parent().parent().next().fadeIn("fast");
        $(this).next().fadeIn("slow");
    });

    $(".preitem").mouseleave(function(){
        $(this).parent().parent().next().fadeOut("fast");
        $(this).next().fadeOut(0);
    });
});