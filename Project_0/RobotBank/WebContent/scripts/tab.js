(function($) {
  $(function() {
    // set active radio to address bar
    $(document).on('click', '.tabs .tabs-nav label', function() {
      var hash = '#' + $(this).attr('for');
      window.history.replaceState('', '', hash);
    });
    // select active radio based on hash
    $(document.location.hash).prop('checked', true);
  });
})(window.Zepto || window.jQuery);