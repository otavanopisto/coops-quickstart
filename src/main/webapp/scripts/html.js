(function() {
  
  /* global CKEDITOR,CONTEXTPATH */
  
  $(document).ready(function() {
    var location = window.document.location;
    var search = location.search;
    if (!search) {
      window.document.location = CONTEXTPATH;
      return;
    }
    
    var fileId = null;
    
    var params = search.substring(1).split('&');
    for (var i = 0, l = params.length; i < l; i++) {
      var param = params[i].split('=', 2);
      if (param[0] === 'fileId') {
        fileId = param[1];
      }
    }
    
    if (!fileId) {
      window.document.location = CONTEXTPATH;
      return;
    }
    
    var serverUrl = CONTEXTPATH + '/rest/coops/' + fileId + '';
  
    CKEDITOR.plugins.addExternal('change', CONTEXTPATH + '/scripts/ckplugins/change/');
    CKEDITOR.plugins.addExternal('coops', CONTEXTPATH + '/scripts/ckplugins/coops/');
    CKEDITOR.plugins.addExternal('coops-connector', CONTEXTPATH + '/scripts/ckplugins/coops-connector/');
    CKEDITOR.plugins.addExternal('coops-dmp', CONTEXTPATH + '/scripts/ckplugins/coops-dmp/');
    CKEDITOR.plugins.addExternal('coops-cursors', CONTEXTPATH + '/scripts/ckplugins/coops-cursors/');
    CKEDITOR.plugins.addExternal('coops-sessionevents', CONTEXTPATH + '/scripts/ckplugins/coops-sessionevents/');
    
    var editor = CKEDITOR.appendTo( 'ckcontainer', {
      skin: 'moono',
      extraPlugins: 'coops,coops-connector,coops-dmp,coops-cursors,coops-sessionevents',
      readOnly: true,
      height: 500,
      coops: {
        serverUrl: serverUrl
      },
      toolbar: [
         { name: 'document', groups: [ 'mode', 'document', 'doctools' ], items: [ 'Print', '-', 'Templates' ] },
         { name: 'clipboard', groups: [ 'clipboard', 'undo' ], items: [ 'Cut', 'Copy', 'Paste', 'PasteText', 'PasteFromWord', '-', 'Undo', 'Redo' ] },
         { name: 'editing', groups: [ 'find', 'selection', 'spellchecker' ], items: [ 'Find', 'Replace', '-', 'SelectAll', '-', 'Scayt' ] },
         { name: 'paragraph', groups: [ 'list', 'indent', 'blocks', 'align', 'bidi' ], items: [ 'NumberedList', 'BulletedList', '-', 'Outdent', 'Indent', '-', 'Blockquote', 'CreateDiv', '-', 'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock', '-', 'BidiLtr', 'BidiRtl' ] },
         '/',
         { name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ], items: [ 'Bold', 'Italic', 'Underline', 'Strike', 'Subscript', 'Superscript', '-', 'RemoveFormat' ] },
         { name: 'links', items: [ 'Link', 'Unlink', 'Anchor' ] },
         { name: 'forms', items: [ 'Form', 'Checkbox', 'Radio', 'TextField', 'Textarea', 'Select', 'Button', 'ImageButton', 'HiddenField' ] },
         { name: 'insert', items: [ 'Image', 'Flash', 'Table', 'HorizontalRule', 'Smiley', 'SpecialChar', 'PageBreak', 'Iframe' ] },
         '/',
         { name: 'styles', items: [ 'Styles', 'Format', 'Font', 'FontSize' ] },
         { name: 'colors', items: [ 'TextColor', 'BGColor' ] },
         { name: 'tools', items: [ 'Maximize', 'ShowBlocks' ] },
         { name: 'others', items: [ '-' ] },
         { name: 'about', items: [ 'About' ] }
       ]
    });
    
    
    /* CoOps status messages */
    
    editor.on("CoOPS:SessionStart", function (event) {
      document.title = $('input[name="name"]').val() + ' - Loaded';
    });
    
    editor.on("CoOPS:ContentDirty", function (event) {
      document.title = $('input[name="name"]').val() + ' - Unsaved';
    });
    
    editor.on("CoOPS:PatchSent", function (event) {
      document.title = $('input[name="name"]').val() + ' - Saving...';
    });
    
    editor.on("CoOPS:PatchAccepted", function (event) {
      document.title = $('input[name="name"]').val() + ' - Saved';
    });

    editor.on("CoOPS:ConnectionLost", function (event) {
      $('.notifications').notifications('notification', 'load', event.data.message).addClass('connection-lost-notification');
    });

    editor.on("CoOPS:Reconnect", function (event) {
      $('.notifications').find('.connection-lost-notification').notification("hide");
    });

    editor.on("CoOPS:CollaboratorJoined", function (event) {
      $('.collaborators').collaborators("addCollaborator", event.data.sessionId, event.data.displayName||'Anonymous', event.data.email||(event.data.sessionId + '@no.invalid'));
    });

    editor.on("CoOPS:CollaboratorLeft", function (event) {
      $('.collaborators').collaborators("removeCollaborator", event.data.sessionId);
    });
    
    // CoOPS Errors
    
    editor.on("CoOPS:Error", function (event) {
      $('.notifications').find('.connection-lost-notification').notification("hide");
      
      switch (event.data.severity) {
        case 'CRITICAL':
        case 'SEVERE':
          $('.notifications').notifications('notification', 'error', event.data.message);
        break;
        case 'WARNING':
          $('.notifications').notifications('notification', 'warning', event.data.message);
        break;
        default:
          $('.notifications').notifications('notification', 'info', event.data.message);
        break;
      }
    });
    
    $('input[name="name"]').change(function (event) {
      var oldValue = $(this).parent().data('old-value');
      var value = $(this).val();
      $(this).parent().data('old-value', value);
      
      editor.fire("propertiesChange", {
        properties : [{
          property: 'title',
          oldValue: oldValue,
          currentValue: value
        }]
      });
    });
    
    editor.on("CoOPS:PatchReceived", function (event) {
      var properties = event.data.properties;
      if (properties) {
        $.each(properties, function (key, value) {
          if (key === 'title') {
            $('input[name="name"]').val(value);
          }
        });
      }
    });
    
    $('.collaborators').collaborators();
  });
  
}).call(this);