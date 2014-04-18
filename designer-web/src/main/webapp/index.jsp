<html>
<link rel="stylesheet" type="text/css"
	href="js/resources/css/ext-all.css">
<script type="text/javascript" src="js/ext-all.js"></script>
<script type="text/javascript" src="js/app-all.js"></script>
<jsp:directive.page import="java.io.*" />
<jsp:directive.page import="javax.swing.*" />
<jsp:directive.page import="java.awt.event.*" />
<jsp:directive.page import="java.awt.*" />
<jsp:directive.page import="java.util.*" />
<jsp:directive.page import="javax.swing.SwingUtilities" />
<jsp:directive.page import="javax.swing.filechooser.*" />

<jsp:directive.page import="java.util.*" />



<script>


function loadApplet(code,codebase,width,height){
	var placeholder=document.getElementById('download');
	var a = document.createElement('object'); 
	a.setAttribute('type','application/x-java-applet'); 
	a.setAttribute('width',width); 
	a.setAttribute('height',height); 
	var codeParam = document.createElement('param'); 
	codeParam.setAttribute('name','code');
	codeParam.setAttribute('value',code); 
	a.appendChild(codeParam); 
	var codebaseParam = document.createElement('param'); 
	codebaseParam.setAttribute('name','codebase');
	codebaseParam.setAttribute('value',codebase);
	a.appendChild(codebaseParam);
	placeholder.appendChild(a); }
	
/*function openApplet()
	{alert("hi");
	var app = document.MyApplet;
    var attributes ={code:'MyApplet',width:500, height:300};
	}*/
</script>

<body>
    <applet code="MyApplet.class" codebase="/../main/java/MyApplet.class" height="300" width="550" />
    <!--  <object name="BrowsePage" type="application/x-java-applet" height="300" width="550" > -->
    <param name="code" value="BrowsePage.class"/>
    </object>
	<script>
 var baseURL = 'http://localhost:8080/desginer-web/services/crud/';
	</script>
	<script type="text/javascript" src="js/app-model.js"></script>
	<script type="text/javascript" src="js/app-store.js"></script>
	
	</body>
	

	<script>
	
	   var baseURL = 'http://localhost:8080/desginer-web/services/crud/';
	   var baseGIT='https://localhost:18443/gitblit/repositories/';
	</script>
	<script type="text/javascript" src="js/app-model.js"></script>
	<script type="text/javascript" src="js/app-store.js"></script>
	<script>
	   
		function createGrid(title, store, columns) {
			  return	Ext.create('Ext.grid.Panel', {
			        title: title,
			        store: store,
			        columns:columns,
			        height: 200,
			        width: 400,
			        name: title,
			        closeable: true,
			        selType: 'rowmodel',
			        plugins: [
			              rowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
			                clicksToEdit: 1, 
			                saveBtnText : 'Update New',
			            })
			        ],
			       dockedItems: [{
			            xtype: 'toolbar',
			            items: [{
			                text: 'Add',
			                iconCls: 'icon-add',
			                handler: function(){
			                    store.insert(0, new EntityAttribute());
			                    rowEditing.startEdit(0, 0);
			                }
			            }, '-', {
			                text: 'Delete',
			                iconCls: 'icon-delete',
			                handler: function(){
			                    var selection = grid.getView().getSelectionModel().getSelection()[0];
			                    if (selection) {
			                        store.remove(selection);
			                    }
			                }
			            }]
			        }] 
			    });
	    }
		
		function createGrid_attr(title, store, columns, id,entityForm) {
			  return	Ext.create('Ext.grid.Panel', {
			        title: title,
			        store: store,
			        id:title,
			        columns:columns,
			        height: 200,
			        width: 400,
			        name: title,
			        closeable: true,
			        selType: 'rowmodel',
			        plugins: [
			              rowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
			                clicksToEdit: 1, 
			                saveBtnText : 'Update New',
			               
			             /*  listeners: {
			                	edit: function(editor, e){
			                	    var record=e.record;
			                	    record.data.id=0;
			                	    var typeid=record.data.type;
			                	    record.data.type={'id':typeid};
			                	    alert(record.data.toSource());
			                	    var complete=submitCreateAttributeFunction(entityForm,record);
			                	    alert(complete.toSource());
			                	    
			                	    Ext.Ajax.request({
			             				url : baseURL + 'entity/',
			             				method : 'POST',
			             				headers : {
			             					'Content-Type' : 'application/json'
			             				},
			             				jsonData : complete,
			             				success:
			             					function(){alert("Successful");},
			             				failure:
			             					function(){alert("Failure");},
			             				
			             			});
			                	     
			            				}
			               				 } */
			              
			            			})
			              ],
			    					
			                		
			                	
			                
			            
			        
			       dockedItems: [{
			            xtype: 'toolbar',
			            items: [{
			                text: 'Add',
			                iconCls: 'icon-add',
			                handler: function(){
			                    store.insert(0, new EntityAttribute());
			                    rowEditing.startEdit(0, 0);
			                }
			            }, '-', {
			                text: 'Delete',
			                iconCls: 'icon-delete',
			                handler: function(){
			                    var selection = grid.getView().getSelectionModel().getSelection()[0];
			                    if (selection) {
			                        store.remove(selection);
			                    }
			                }
			            }]
			        }] 
			    });
	    }
		
		function submitCreateAttributeFunction()
		{
			var formPanel = this.up('form');
			var form = formPanel.getForm();
            var formValues = form.getValues();
			formValues.parentPackage  = {'id': form.findField('parentPackage.id').getValue()  };
			var gridData = new Array();
			for (var j=0; j<=Ext.getCmp('Attributes').getStore().getCount()-1; j++) {
			   Ext.getCmp('Attributes').getSelectionModel().select(j,true);
			   if(Ext.getCmp('Attributes').store.getAt(j).data.type < 100)
				 {
			       var typeid=Ext.getCmp('Attributes').store.getAt(j).data.type;
			       Ext.getCmp('Attributes').store.getAt(j).data.type={'id':typeid};
			     }
			    if(Ext.getCmp('Attributes').getSelectionModel().isSelected(j)){
			    	if(Ext.getCmp('Attributes').store.getAt(j).data.id > 0)
		               gridData.push(Ext.getCmp('Attributes').store.getAt(j).data);
			    	else{
			    		Ext.getCmp('Attributes').store.getAt(j).data.id=0;
			    		gridData.push(Ext.getCmp('Attributes').store.getAt(j).data);
			    	}
			    		
		      }
			   
			                }
			formValues.entityAttributes = gridData;
		    return formValues; 

		}
	  		
	  		
		function getEntityFormItems() {
	
			return [ { name : 'id', xtype: 'hiddenfield',allowBlank : false, value : 0},
			         { fieldLabel : 'Name', name : 'name', allowBlank : false},
			         { fieldLabel : 'Description', name : 'description', allowBlank : false},
				     Ext.create('Ext.form.ComboBox', {
					    fieldLabel : 'Package',
					    name : 'parentPackage.id',
					    store : packageStore,
					    queryMode : 'local',
					    displayField : 'name',
					    valueField : 'id'
				    } )];
		};

		var ctxMenu = Ext.create('Ext.menu.Menu', {
			items : [ {
				text : 'Add Attribute',
				action : 'addattribute'
			}, {
				text : 'Edit',
				action : 'edit'
			}, {
				text : 'Delete',
				action : 'delete'
			} ]
		});

		var containerCtxMenu = Ext.create('Ext.menu.Menu', {
			items : [ {
				text : 'New Package',
				action : 'newpackage',
				handler : clickHandler
			}, {
				text : 'New Entity',
				action : 'addentity',
				handler : clickHandler
			}, {
				text : 'Manage Type',
				action : 'managetype',
				handler : clickHandler
			}, {
				text : 'Add Type',
				action : 'addtype',
				handler : clickHandler
			}, {
				text : 'Refresh',
				action : 'refresh'
			} ]
		});

		var treePanel = Ext.create('Ext.tree.Panel', {
			contextMenu : ctxMenu,
			title : 'Entity Explorer',
			width : 200,
			height : 150,
			store : store,
			rootVisible : false,
			region : 'west',
			listeners : {
				itemclick : function(view, rec, item, index, eventObj) {
					if (rec.raw.leaf == true)
						modifyEntityForm(rec.raw.id);
					else
						createPackageForm();
				}
			},
			columns : [ {
				xtype : 'treecolumn',
				header : 'Packages',
				dataIndex : 'text',
				flex : 1
			} ]
		});

		var tabs = Ext.create('Ext.tab.Panel', {
			width : 900,
			height : 400,
			region:'center'
		});
		
		
		var resultsPanel = Ext.create('Ext.panel.Panel', {
		    height: 30,
		    region: 'north'
		});
		
		var win ;
		var minAppWin; 
		var miniApp;
		function createMiniApp( response ) {
			 miniApp = Ext.JSON.decode(response.responseText);
			
			if( response.status == "200")
			   win.hide();
			
			var items = new Array();
			
			if ( miniApp.db ) {
				items.push({
		            xtype: 'fieldset',
		            columnWidth: 0.5,
		            title: 'DB Config',
		            collapsible: true,
		            defaultType: 'textfield',
		            defaults: {
		                anchor: '100%'
		            },
		            layout: 'anchor',
		            items: [{name:'jdbcDriver',fieldLabel:'Driver Name'},
		                    {name:'jdbcURL',fieldLabel:'JDBC URL:'},
		                    {name:'username',fieldLabel:'Username:'},
		                    {name:'password',fieldLabel:'Password:'}
		                   ] 
		            });
			} 
			
			if( miniApp.persistenceAPI ) {
				items.push({
		            xtype: 'fieldset',
		            columnWidth: 0.5,
		            title: 'Persistence API Config',
		            collapsible: true,
		            defaultType: 'textfield',
		            defaults: {
		                anchor: '100%'
		            },
		            layout: 'anchor',
		            items: [{name:'inheritence',fieldLabel:'Inheritence'},
		                    {name:'idgeneration',fieldLabel:'Id Generation'},
		                    {name:'association',fieldLabel:'Association'}
		                   ] 
		            });
			}
			
			if( miniApp.securityAPI ) {
				
			}
			
			if( miniApp.securityStore) {
				if( miniApp.securityStore == "LDAP" ) {
					
					items.push({
			            xtype: 'fieldset',
			            columnWidth: 0.5,
			            title: 'LDAP Config',
			            collapsible: true,
			            defaultType: 'textfield',
			            defaults: {
			                anchor: '100%'
			            },
			            layout: 'anchor',
			            items: [{name:'userProvider',fieldLabel:'Provider'},
			                    {name:'userFilter',fieldLabel:'Filter'},
			                    {name:'authzIdentity',fieldLabel:'Identity'}
			                   ] 
			            });
				} else if ( miniApp.securityStore =="RDBMS") {
					
				} else if ( miniApp.securityStore =="OPENID") {
					
				} else if (miniApp.securityStore == "FACEBOOK" ) {
					
					items.push({
			            xtype: 'fieldset',
			            columnWidth: 0.5,
			            title: 'FaceBook Config',
			            collapsible: true,
			            defaultType: 'textfield',
			            defaults: {
			                anchor: '100%'
			            },
			            layout: 'anchor',
			            items: [{name:'facebook.clientId',fieldLabel:'ClientId'},
			                    {name:'facebook.clientSecret',fieldLabel:'ClientSecret'}
			                   ] 
			            });
				};
			}
			
			if( miniApp.frontend) {
				
			}
			minAppWin =  Ext.create('widget.window', {
                title: 'Package Selection',
                closable: true,
                closeAction: 'hide',
                width: 650,
                height: 550,
                layout: 'border',
                bodyStyle: 'padding: 5px;',
                items: [{
                	xtype: 'form',
                	width : 650,
                    region: 'west',
                    title: 'Small - Single Tier App Config',
                    collapsible: true,
                    floatable: false,
                    layout: 'form',
                    defaultType: 'textfield',
                    items:  items,
                    buttons: [{
                        text: 'Reset',
                        handler: function() {
                            this.up('form').getForm().reset();
                        }
                    }, {
                        text: 'Create',
                        formBind: true, 
                        disabled: true,
                        handler: function() {
                            var form = this.up('form').getForm();
							var formData=form.getValues();
							alert(formData.toSource());
							formData.app={'id':miniApp.id};
							var a=form.findField('jdbcURL').getValue();
							var b=form.findField('jdbcDriver').getValue();
							var c=form.findField('username').getValue();
							var d=form.findField('password').getValue();
							formData.dbConfig={'id':0, 'jdbcURL':a, 'jdbcDriver':b, 'username':c, 'password':d, 'name':miniApp.db};
							var e=form.findField('inheritence').getValue();
							var f=form.findField('association').getValue();
							var g=form.findField('idgeneration').getValue();
							formData.persistenceAPIConfig={'id':0, 'inheritence':e, 'association':f, 'idgeneration':g, 'name':miniApp.persistenceAPI};
							formData.securityAPIConfig={'id':0, name:miniApp.securityAPI};
							if(miniApp.securityStore=="LDAP"){
								var h=form.findField('userProvider').getValue();
								var i=form.findField('userFilter').getValue();
								var j=form.findField('authzIdentity').getValue();
								formData.securityStoreConfig={'lDAPConfig':{'id':0, 'userProvider':h, 'userFilter':i, 'authzIdentity':j, name:miniApp.securityStore}, 'id':0, name:miniApp.securityStore};
							}
							
							else if(miniApp.securityStore=="Facebook"){
								var h=formData.findField('clientId').getValue();
								var i=formData.findField('clientSecret').getValue();
								
								formData.securityStoreConfig={'facebookConfig':{'id':0, 'clientId':h, 'clientSecret':i, name:miniApp.securityStore}, 'id':0, name:miniApp.securityStore};
								
								
								
							}
							formData.name=miniApp.name;
							formData.description=miniApp.description;
							formData.id=0;
							Ext.Ajax.request({
		        				url : baseURL + 'AppConfig/',
		        				method : 'POST',
		        				headers : {
		        					'Content-Type' : 'application/json'
		        				},
		        				jsonData : formData,
		        				success :function() {alert("Yipee!! Success!");
		        				
		        					Ext.Ajax.request({
		        						url: baseURL+'autogen/app/small/'+miniApp.id,
		        						method: 'POST',
		        						headers : {
				        					'Content-Type' : 'application/json'
				        				},
				        				timeout:600000,
				        				success:function() {
				        				alert("App made success");
				        				
				        				Ext.getCmp('download').show();
				        			
				        				},
				        				failure : function(){ alert("App not made");
				        				 window.location.reload();
				        				 }
				        				});
				        				},
		        					
		        				failure : function(){ alert("Failed");
		        				 window.location.reload();}
		        				
		        				
		        			});
							
							
                           
                        }
                    
                    
                    
                    }]
                    
                }]
			});
			
			minAppWin.show(viewport,  function () {});
			
		}
		
	
			win =  Ext.create('widget.window', {
                title: 'Download',
                closable: true,
                closeAction: 'hide',
                id:'download',
                resizable: true,
                draggable: true,
                //animateTarget: this,
                width: 100,
                height: 100,
                layout: 'border',
                bodyStyle: 'padding: 5px;',
             
                	buttons: [{
                        text: 'Download',
                        handler: function() {
                        	var test=window.open();
                        	test.location='http://localhost:8080/desginer-web/download?fileName='+miniApp.name;

        					/*Ext.Ajax.request({
        						url: 'http://localhost:8080/desginer-web/download?fileName='+miniApp.name,
        						method: 'GET',
        						headers : {
		        					'Content-Type' : 'application/json'
		        				},
		        				success:function() {
		        				alert("File Download Successful");
		        				
		        				Ext.getCmp('download').show();
		        			
		        				},
		        				failure : function(){ alert("File downlaod failed");
		        				 window.location.reload();
		        				 }
		        				});*/
                        }
                }]
            	
			});
			
		



		
			
			
			/*function saveFileToDisk(url, dest, callback) {
			    var xhr = new XMLHttpRequest();

			    xhr.onload = function() {
			        var typedData = new Uint8Array(xhr.response),
			            ln = typedData.length,
			            data = new Array(ln),
			            result;

			        for (var i = 0; i < ln; i++) {
			            data[i] = typedData[i];
			        }

			        result = Ion.io.writeFile(dest, data);
			        callback(result.success);
			    };

			    xhr.onerror = function() {
			        callback(false);
			    };

			    xhr.open('GET', url);
			    xhr.responseType = 'arraybuffer';
			    xhr.send(null);
			}*/
		

		
		
			
		function newApp (item, e) {
			win =  Ext.create('widget.window', {
	                title: 'App Selection',
	                closable: true,
	                closeAction: 'hide',
	                resizable: true,
	                draggable: true,
	                //animateTarget: this,
	                width: 650,
	                height: 550,
	                layout: 'border',
	                bodyStyle: 'padding: 5px;',
	                items: [{
	                	xtype: 'form',
	                    region: 'west',
	                    title: 'Small - Single Tier',
	                    width: 200,
	                    split: true,
	                    collapsible: true,
	                    floatable: false,
	                    layout: 'form',
	                    defaultType: 'textfield',
	                    items: [
	                            {
	                            	name:'name',
	                            	fieldLabel:'App Name'
	                            },
	                            {
	                            	name: 'description',
	                            	fieldLabel: 'Description'
	                            },
	                            {
	                            	name:'basePackage',
	                            	fieldLabel:'Base Package'
	                            	
	                            },
	                    	Ext.create('Ext.form.ComboBox', {
	                    		name :'db',
	                    	    fieldLabel: 'DB Type',
	                    	    store: dbTypes,
	                    	    queryMode: 'local',
	                    	    displayField: 'name',
	                    	    valueField: 'abbr',

	                    	}), 
	                    	Ext.create('Ext.form.ComboBox', {
	                    		name: 'persistenceAPI',
	                    	    fieldLabel: 'Persistence API',
	                    	    store: persistenceAPITypes,
	                    	    queryMode: 'local',
	                    	    displayField: 'name',
	                    	    valueField: 'abbr',

	                    	}),
	                        Ext.create('Ext.form.ComboBox', {
	                        	name: 'securityAPI',
	                    	    fieldLabel: 'Security API',
	                    	    store: securityAPITypes,
	                    	    queryMode: 'local',
	                    	    displayField: 'name',
	                    	    valueField: 'abbr',

	                    	}),
	                        Ext.create('Ext.form.ComboBox', {
	                        	    name:'securityStore',
		                    	    fieldLabel: 'Security Store',
		                    	    store: securityStoreTypes,
		                    	    queryMode: 'local',
		                    	    displayField: 'name',
		                    	    valueField: 'abbr',

		                    }),
		                    Ext.create('Ext.form.ComboBox', {
		                    	name:'frontend',
	                    	    fieldLabel: 'Frontend',
	                    	    store: frontendTypes,
	                    	    queryMode: 'local',
	                    	    displayField: 'name',
	                    	    valueField: 'abbr',
	                        })
		                    
	                    	],
	                    buttons: [{
	                        text: 'Reset',
	                        handler: function() {
	                            this.up('form').getForm().reset();
	                        }
	                    }, {
	                        text: 'Create',
	                        formBind: true, 
	                        disabled: true,
	                        handler: function() {
	                            var form = this.up('form').getForm();

	                            if (form.isValid()) {
	                            	ajaxRequest(form, 'app', form.getValues(),createMiniApp);
	                            }
	                        }
	                    }]
	                }, {
	                    region: 'center',
	                    title: 'Medium - 3 Tier',
	                    width: 200,
	                    split: true,
	                    collapsible: true,
	                    floatable: false
	                },{
	                    region: 'east',
	                    title: 'Large - Multi Tier',
	                    width: 200,
	                    split: true,
	                    collapsible: true,
	                    floatable: false
	                }]
	            });
			win.show(this,  function () {});
		}
		var menu = Ext.create('Ext.menu.Menu', {
	        items: [               
	            {text: 'App' , handler: newApp },{text: 'Package', handler: newPackage}, {text: 'Entity', handler: newEntity },{text: 'Page'},{text: 'Menu'},'-', 
	            {
	                text: 'Config',
	                menu: {        
	                    items: [
	                        '<b class="menu-title">Config Category</b>',
	                        {
	                            text: 'Persistence',
	                            group: 'theme'
	                        }, {
	                            text: 'Event Broadcasting',
	                            checked: false,
	                            group: 'theme'
	                        }, {
	                            text: 'Security',
	                            menu: {
	                            	items: [
	                            	        { text: 'SSO' }, {text: 'Social'} ,{ text: 'Local DB'}, { text: 'Local LDAP'}
	                            	       ]
	                            }
	                        }, {
	                            text: 'Cache',
	                            checked: false,
	                            group: 'theme'
	                        },
	                        {
	                            text: 'Validation',
	                            checked: false,
	                            group: 'theme'
	                        },
	                        {
	                            text: 'Frontend',
	                            checked: false,
	                            group: 'theme'
	                        }
	                    ]
	                }
	           }
	        ]
	    }); 
		
		Ext.onReady(function() {
		    resultsPanel.addDocked({ xtype: 'toolbar',
                 dock: 'bottom',
                 items: [{text: 'File' },{
                     text:'New',
                     iconCls: 'bmenu', 
                     menu: menu  
                 }]
		    });
		});
		
		var viewport = new Ext.Viewport({
			title : 'BorderLayout Demo',
			layout : 'border',
 			items : [ resultsPanel, treePanel, tabs, {
				xtype : 'panel',
				title : 'East Panel',
				region : 'east',
				split : true,
				width : 200
			}, {
				xtype : 'panel',
				title : 'South Panel',
				region : 'south',
				split : true,
				collapsible : true,
				collapseMode : 'mini',
				height : 150
			} ]
		});

		function addToViewPort(panel) {
			tabs.setActiveTab(tabs.add(panel));
		}
		function clickHandler(item, e, eOpts) {
			if (item.action == "newpackage")
				createPackageForm();
			else if (item.action == "managetype")
				manageTypeForm();
			else if (item.action == "addtype")
				addNewType();
			else if (item.action == "addentity")
				addNewEntity();
		};

		function addNewEntity() {
			var entityNewForm = createForm('Add Entity', 'entity/',getEntityFormItems(), submitCreateEntityFunction);

			addToViewPort(entityNewForm);
		}

		
		function addNewType() {
			var items = [{ name : 'id', xtype: 'hiddenfield',allowBlank : false, value : 0},
			   {
				fieldLabel : 'Name',
				name : 'name',
				allowBlank : false
			}, {
				fieldLabel : 'Description',
				name : 'description',
				allowBlank : false
			}, {
				fieldLabel : 'Class Name',
				name : 'className',
				allowBlank : false
			} ];
			var typeForm = createForm('Add Type', 'type/', items);

			addToViewPort(typeForm);
		}

		function manageTypeForm() {
			var columns = [ {
				text : 'Name',
				dataIndex : 'name'
			}, {
				text : 'Class Name',
				dataIndex : 'className',
				flex : 1
			}, {
				text : 'Description',
				dataIndex : 'description'
			} ];

			var typeGrid = createGrid('Manage Types', typeStore, columns);
			addToViewPort(typeGrid);

		}

		function createPackageForm() {
			var items = [ {
				fieldLabel : 'Name',
				name : 'name',
				allowBlank : false
			}, {
				fieldLabel : 'Description',
				name : 'description',
				allowBlank : false
			} ];
			var packageForm = createForm('Add Package', 'package/', items);
			// viewport.getComponent(1).add(packageForm);

			addToViewPort(packageForm);
		}

		function modifyEntityForm(id) {

			var entityForm = createForm('Modify Entity', 'entity/', getEntityFormItems(),submitCreateAttributeFunction);
			
			var columns = [ { text: 'id', dataIndex: 'id', hidden:true,  editor: 'textfield'},
			               {text : 'Name',dataIndex : 'name',editor: 'textfield'}, 
			               {text : 'Description',dataIndex : 'description',editor: 'textfield'},
			             {text : 'Is Searchable',dataIndex : 'searchable',editor:new Ext.form.field.ComboBox({
                               typeAhead:true,
                               triggerAction:'all',
                               store:optionStore,
                               editable:true,
                               selectOnTab:true,
                               valueField: 'name',
                               displayField: 'abbr'
                               
                               
                            	})},
                            	
			               {text : 'Type',dataIndex : 'type',editor: new Ext.form.field.ComboBox({
                               typeAhead:true,
                               triggerAction:'all',
                               editable:true,
                               selectOnTab:true,
                               store:typeStore,
                               name:'id',
                               displayField: 'name', 
                               valueField: 'id',
                               queryMode : 'local'
                            	
                                      
                               
                           })}
                            	
                            	];
			

			Ext.Ajax.request({
				url : baseURL + 'entity/' + id,
				method : 'GET',
				success : function(response) {
					var data = Ext.JSON.decode(response.responseText.trim());
					var form = entityForm.getForm();
					form.findField('id').setValue(data.id);
					form.findField('name').setValue(data.name);
					form.findField('description').setValue(data.description);
				    form.findField('parentPackage.id').setValue(data.parentPackage.id);
					
					var attributeStore = Ext.create('Ext.data.JsonStore', {
				        model: 'EntityAttribute',
				        data: data.entityAttributes
				        
				    });
				
					
					var attributeGrid = createGrid_attr('Attributes', attributeStore, columns, id,entityForm); 

					entityForm.add(attributeGrid);
					addToViewPort(entityForm);
					
				}
			});
		}

		function createForm(title, resource, items ,submitFunction ) {
			
			return Ext.create('Ext.form.Panel', {
				title : title,
				bodyPadding : 5,
				width : 350,
				layout : 'anchor',
				closable : true,
				waitMsgTarget : true,
				defaults : {
					anchor : '100%'
				},
				defaultType : 'textfield',
				items : items,
				buttons : [ {
					text : 'Reset',
					handler : function() {
						this.up('form').getForm().reset();
					}
				}, {
					text : 'Submit',
					formBind : true,
					disabled : true,
					preSubmit : submitFunction,
					handler : function() {
						var formValues = null;

						var form = this.up('form').getForm();
						if( this.preSubmit != undefined ) {
							formValues  = this.preSubmit();
						}else {
							formValues = form.getValues();
						}
						if (form.isValid()) {
							ajaxRequest(form, resource, formValues,shandler);
						}
						
					} 
				} ]
			});
		}
		function shandler()
		{
			//Ext.Msg.alert(Success);
			window.location.reload();
		}

		function submitCreateEntityFunction() {
			var formPanel = this.up('form');
			var form = formPanel.getForm();
            
			var formValues = form.getValues();
			
		  	//alert(formValues.toSource()); 
			
			formValues.parentPackage  = {'id': form.findField('parentPackage.id').getValue()  };
			var gridRecords = formPanel.items.items[3].getStore().data.items;
			
			var gridData = new Array();
			for(var i =0; i < gridRecords.length ; i++)  {
			      gridData.push(gridRecords[i].data);
		    }
			//formValues.entityAttributes = gridData;
			alert(formValues.toSource());
		    return formValues; 
		}
		
		function ajaxRequest(form, resource , formValues, successHandler) {
           if( successHandler == undefined) {
        	   successHandler = function (response) {
        		   Ext.Msg.alert('Success', response.status);
        	   };
           }
			
			Ext.Ajax.request({
				url : baseURL + resource,
				method : 'POST',
				headers : {
					'Content-Type' : 'application/json'
				},
				jsonData : formValues,
				success : successHandler,
					
					
			   // function(response) {
					//Ext.Msg.alert('Success', response.status);
					//var jsonData = Ext.util.JSON.decode(response.responseText);
					//alert(jsonData.id);
					//alert(response.status);
				//},
				failure : function(form, action) {
					Ext.Msg.alert('Failed', 'Server Error');
				}
			});
		}

		treePanel.on('itemcontextmenu', function(view, record, item, index, event) {
			ctxMenu.showAt(event.getXY());
			event.stopEvent();
		}, this);

		treePanel.on('containercontextmenu', function(view, event, t) {
			containerCtxMenu.showAt(event.getXY());
			event.stopEvent();
		}, this);
		
		function newEntity(item, e) {
			var entityNewForm = createForm('Add Entity', 'entity/',getEntityFormItems(), submitCreateEntityFunction);

			addToViewPort(entityNewForm);
		}
		
		function newPackage(item, e){
			createPackageForm();
			
		}
		
		
		
	</script>
</body>
</html>
