var BrowserHistoryManager = function(loaderApp){
    window.onpopstate = function(event) {
        if (event.state.toRevert === 'hideAppendUrlDialog'){
            loaderApp.appendUrlDialog.hideAppendUrlDialog();
        }
        else if (event.state.toRevert === 'hideUrlSettingsDialog'){
            loaderApp.urlSettingsDialog.hideUrlSettingsDialog();
        }
        else if (event.state.toRevert === 'slideFromSettingsView'){
            loaderApp.settingsView.cancel();
        }
    }
}

BrowserHistoryManager.prototype = {
    toAppendUrlDialog: function(){
        window.history.replaceState({'toRevert': 'hideAppendUrlDialog' }, 
                                    document.title, location.href);
        window.history.pushState('appendUrlView', document.title, location.href);
    },
    toUrlSettingsDialog: function(){
        window.history.replaceState({'toRevert': 'hideUrlSettingsDialog' }, 
                                    document.title, location.href);
        window.history.pushState('urlSettings', document.title, location.href);
    },
    toSettingsView: function(){
        window.history.replaceState({'toRevert': 'slideFromSettingsView' }, 
                                    document.title, location.href);
        window.history.pushState('settingsView', document.title, location.href);
    }
}
