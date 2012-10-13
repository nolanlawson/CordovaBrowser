var AppendUrlDialog = function(transitioner){
    this.transitioner = transitioner;
    this.appendUrlDialog = $('#appendUrlDialog');
    this.appendUrlInput = $('#appendUrlInput');
    this.appendUrlInput.onenter(this.acceptUrlInput.bind(this));
    this.appendUrlCancel = $('#appendUrlCancel');
    this.appendUrlCancel.onAndroidTap(this.hideAppendUrlDialog.bind(this));
}
AppendUrlDialog.prototype = {
    acceptUrlInput: function(){
        var url = this.parseUrl(this.appendUrlInput.val());
        this.hideAppendUrlDialog();
        this.resultCB(url);
    },
    showAppendUrlDialog: function(resultCB){
        this.resultCB = resultCB;
        this.transitioner.downDialog(this.appendUrlDialog, 
            this.appendUrlInput.focus.bind(this.appendUrlInput));  
    },
    hideAppendUrlDialog: function(){
        this.transitioner.upDialog(this.appendUrlDialog);
        this.appendUrlInput.val("");
    },
    parseUrl: function(url){
        if (url.length === 0)
            return;
        if (url.indexOf('http') !== 0) {
            url = 'http://' + url;
        }
        if (url.indexOf('.') === -1){
            url = url + '.com';
        }
        return url;
    }
}
