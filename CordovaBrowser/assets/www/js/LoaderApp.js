var LoaderApp = function(){
    this.setup();
    this.addHistory();
}

LoaderApp.prototype = {
    setup: function(){
        this.transitioner = new Transitioner();
        this.savedHistory = new SavedHistory();
        this.appendUrlDialog = new AppendUrlDialog(this.transitioner);
        this.settingsView = new SettingsView(this.transitioner, 
                                    this.restoreUrlView.bind(this));
        this.urlSettingsDialog = new UrlSettingsDialog(this.transitioner,
                                    this.deleteItem.bind(this));
        this.browserHistoryManager = new BrowserHistoryManager(this);

        this.moreButton = $('#moreButton');
        this.moreButton.onAndroidTap(
            function(){
                this.transitioner.fadeAway(this.moreButton);
                this.transitioner.slideLeft(this.urlView, this.settingsView.div);
                this.browserHistoryManager.toSettingsView();
        }.bind(this));

        this.urlView = $('#urls');
        this.appendUrlButton = $('#appendUrl');
        this.appendUrlButton.onAndroidTap(this.requestAppendUrl.bind(this));
    },
    requestAppendUrl: function(){
        this.appendUrlDialog.showAppendUrlDialog(this.appendUrl.bind(this))
        this.browserHistoryManager.toAppendUrlDialog();
    },
    appendUrl: function(url){
        if (url !== undefined){
            this.slideUrlToView(url);
            this.savedHistory.addUrlToHistory(url);
        }
    },
    restoreUrlView: function(result){
        this.transitioner.fadeIn(this.moreButton);
        this.transitioner.slideRight(this.urlView, this.settingsView.div, function(){
            if (result.deletedAll){
                for (var i = 0; i < this.urlView.children().size() - 1; i++){
                    var child = this.urlView.children().eq(i);
                    this.deleteItem(child);
                }
            }
        }.bind(this));
    },
    createUrlButton: function(url){
        var listItem = $('<li>');
        var urlButton = $('<button>');
        urlButton.html(url);
        urlButton.addClass('urlButton'); 
        urlButton.addClass('androidButton');
        listItem.append(urlButton);
        var callGotoUrl = function(){
            this.gotoUrl(url);
        };
        var callUrlSettings = function(){
            this.urlSettingsDialog.showUrlSettingsDialog(listItem);
            this.browserHistoryManager.toUrlSettingsDialog();
        };
        urlButton.onAndroidTap(callGotoUrl.bind(this), callUrlSettings.bind(this))
        return listItem;
    },
    addUrlToView: function(url){
        var item = this.createUrlButton(url);
        this.urlView.prepend(item);
    },
    slideUrlToView: function(url){
        var item = this.createUrlButton(url);
        this.transitioner.slideListItemDown(this.urlView, item);
    },
    gotoUrl: function(url){
    	cordova.exec(function(winParam) {}, function(error) {}, "Loader",
                "load", [url]);
    },
    deleteItem: function(item){
        var index = item.index();
        var size = item.parent().find('li').size() - 1;
        this.savedHistory.removeIndexFromHistory(size - index - 1);
        this.transitioner.removeListItem(item);
    },
    addHistory: function() {
        var history = this.savedHistory.getHistory();
        for (var i = 0; i < history.length; i++){
            this.addUrlToView(history[i]);
        }
    }
}



