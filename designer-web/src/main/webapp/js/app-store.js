/*var typeStore = Ext.create('Ext.data.Store', {
    fields: ['abbr', 'name'],
    data : [
        {"abbr":"INT", "name":"int"},
        {"abbr":"DOUBLE", "name":"double"},
        {"abbr":"FLOAR", "name":"float"},
        {"abbr":"CHAR", "name":"char"}
    ]
});
*/


	
var dbTypes = Ext.create('Ext.data.Store', {
    fields: ['abbr', 'name'],
    data : [
        {"abbr":"MYSQL", "name":"MySQL"},
        {"abbr":"ORACLE", "name":"ORACLE"},
        {"abbr":"POSTGRES", "name":"POSTGRES"},
        {"abbr":"MONGODB", "name":"MongoDB"},
        {"abbr":"CASSANDRA", "name":"Cassandra"}
    ]
});


var persistenceAPITypes = Ext.create('Ext.data.Store', {
    fields: ['abbr', 'name'],
    data : [
        {"abbr":"JPA", "name":"JPA"},
        {"abbr":"JDO", "name":"JDO"},
        {"abbr":"HIBERNATE", "name":"Hibernate"}
    ]
});

var optionStore = Ext.create('Ext.data.Store', {
    fields: ['abbr', 'name'],
    data : [
        {"abbr":"no", "name":false},
        {"abbr":"yes", "name":true}
    ]
});

var securityAPITypes = Ext.create('Ext.data.Store', {
    fields: ['abbr', 'name'],
    data : [
        {"abbr":"JAAS", "name":"JAAS"},
        {"abbr":"SPRINGSECURITY", "name":"Spring Security"}
    ]
});

var securityStoreTypes = Ext.create('Ext.data.Store', {
    fields: ['abbr', 'name'],
    data : [
        {"abbr":"LDAP", "name":"Ldap"},
        {"abbr":"RDBMS", "name":"Datasource"},
        {"abbr":"OPENID", "name":"OpenID"},
        {"abbr":"FACEBOOK", "name":"Facebook"}
        
    ]
});

var frontendTypes = Ext.create('Ext.data.Store', {
    fields: ['abbr', 'name'],
    data : [
        {"abbr":"JSP", "name":"Jsp"},
        {"abbr":"JSF", "name":"JavaServer Faces"},
        {"abbr":"PHP", "name":"PHP"},
        {"abbr":"PORTLET", "name":"Portlet"},
    ]
});



var serviceAPITypes = Ext.create('Ext.data.Store', {
    fields: ['abbr', 'name'],
    data : [
        {"abbr":"RESTXML", "name":"REST/XML"},
        {"abbr":"RESTJSON", "name":"REST/JSON"},
        {"abbr":"SOAP", "name":"SOAP"},
        {"abbr":"XMLHTTP", "name":"XML/HTTP"},
    ]
});
	



Ext.create('Ext.form.ComboBox', {
    fieldLabel: 'Choose State',
    store: dbTypes,
    queryMode: 'local',
    valueField: 'abbr',

    tpl: Ext.create('Ext.XTemplate',
        '<tpl for=".">',
            '<div class="x-boundlist-item">{abbr} - {name}</div>',
        '</tpl>'
    ),
    // template for the content inside text field
    displayTpl: Ext.create('Ext.XTemplate',
        '<tpl for=".">',
            '{abbr} - {name}',
        '</tpl>'
    )
});


		
		var packageStore = Ext.create('Ext.data.Store', {
		    model: 'Package2',
		    proxy: {
		        type: 'ajax',
		        url : baseURL +'package/',
		        reader: {
		            type: 'json',
		            model: 'Package2'
		        }
		    },
		    autoLoad: true
		});  
		var typeStore = Ext.create('Ext.data.Store', {
		    model: 'Type',
		    proxy: {
		        type: 'ajax',
		        url : baseURL +'type/',
		        reader: {
		            type: 'json',
		            model: 'Type'
		        }
		    },
		    autoLoad: true
		});  
		

		var store = Ext.create('Ext.data.TreeStore', {
			model : 'Package',
			root : {
				name : 'Package',
				expanded : true
			}
		});