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
        {"abbr":"No", "name":false},
        {"abbr":"Yes", "name":true}
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

var selectStore = Ext.create('Ext.data.Store', {
    fields: ['abbr', 'name'],
    data : [
        {"abbr":"type", "name":"Primitive"},
        {"abbr":"entity", "name":"Composite"},
        
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
		
		var entityStore=Ext.create('Ext.data.Store', {
		    model: 'Entity',
		    proxy: {
		        type: 'ajax',
		        url : baseURL +'entity/',
		        reader: {
		            type: 'json',
		            model: 'Entity'
		        }
		    },
		    autoLoad: true
		});  
		
	 
		var collectionStore = Ext.create('Ext.data.Store', {
		    fields: ['abbr', 'name'],
		    data : [
		        {"abbr":"list", "name":"List"},
		        {"abbr":"set", "name":"Set"},
		        {"abbr":"null", "name":"null"},
		        
		        
		    ]
		});

		var validStore = Ext.create('Ext.data.Store', {
		    fields: ['abbr', 'name'],
		    data : [
		        {"abbr":"size", "name":"Size"},
		        {"abbr":"email", "name":"Email"},
		        {"abbr":"creditCard", "name":"CreditCard"},
		        {"abbr":"assertTrue", "name":"AssertTrue"},
		        {"abbr":"assertFalse", "name":"AssertFalse"},
		     
		        
		        
		    ]
		});
		

		var store = Ext.create('Ext.data.TreeStore', {
			model : 'Package',
			root : {
				name : 'Package',
				expanded : true
			},
//		 proxy: {
//	            type: 'ajax',
//	            type : 'json',
//	            root : 'data',
//	            	url : 'jsontreestruct.do'
//	            
//
//	           }
		});