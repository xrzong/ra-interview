$(document).ready(function(){
  $("#searchButton").click(function(){
    var searchKey = $("#searchBar").val();

    var searchUrl = "http://www.cse.scu.edu/~bsteichen/search/search.php?";
    var engParams = {
      "searchText": searchKey,
      "source": "News",
      "results": 10,
      "offset": 0,
      "market": "en-US"
    }

    var searchQuery = searchUrl + $.param(engParams);

    $.get(searchQuery, function(resp, status) {
      $("#english .panel-body").text(resp);
    });

    var translationUrl = "http://www.cse.scu.edu/~bsteichen/search/translate.php?"
    var translationParams = {
      "text": searchKey,
      "to": "zh-CN"
    }
    var translationQuery = translationUrl + $.param(translationParams);
    
    $.get(translationQuery, function(resp, status) { 
      var cnParams = {
        "searchText": resp,
        "source": "Web",
        "results": 10,
        "offset": 0,
        "market": "zh-CN"
      }
      var cnSearchQuery = searchUrl + $.param(cnParams);

      $.get(cnSearchQuery, function(resp, status) {
        $("#chinese .panel-body").text(resp);
      });
    });
  });
});
