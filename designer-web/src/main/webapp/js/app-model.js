		Ext.define('Package', {
			extend : 'Ext.data.Model',
			fields : [ {name : 'id',type : 'int'}, 
			           {name : 'text',type : 'string'} ],
			proxy : {
				type : 'ajax',
				api : {
					create : 'createPackages',
					read : baseURL + 'entity/package/tree',
					update : 'updatePackages',
					destroy : 'destroyPackages'
				}
			}
		});
		
		
		
		Ext.define('Package2', {
		    extend: 'Ext.data.Model',
		    fields: ['id', 'name', 'description']
		});
		
		Ext.define('EntityAttribute', {
			 extend: 'Ext.data.Model',
		     fields : ['id','name','description','searchable','type']
		} );
		
		
		
		Ext.define('Entity2',{
		
		    extend: 'Ext.data.Model',
			fields : ['id','name','description'],
			associations: [
			               { type: 'hasOne', model: 'Package2' }, 
			               { type: 'hasMany' , model : 'EntityAttribute'}
			              ]
		});
		
		
		Ext.define('Type' , {
			 extend: 'Ext.data.Model',
			 fields : ['id','className','name','description']
		});
		