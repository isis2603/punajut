# Tabla de Contenidos

-  [Introducción](#introducción)
-  [Modulo Inicial Book](#módulo-inicial-book)
*  [Archivo book.mod.js](#bookmodjs)
*  [Archivo book.ctrl.js](#bookctrljs)
*  [Archivo book.svc.js](#booksvcjs)
*  [Comportamiento dinámico en el template book.tpl.html](#comportamiento-dinámico-en-el-template-booktplhtml)
*  [Configuración del módulo bookModule en el app.js](#configuración-del-módulo-bookmodule-en-el-appjs)
-  [Módulos mocks](#módulos-mocks)

## Introducción
Al finalizar este paso el estudiante debe estar en capacidad de crear módulos de AngularJS, asociar controladores, servicios y templates con contenido dinámico. 
Las siguientes instrucciones muestran el paso a paso para la construcción del módulo **bookModule** (controlador, servicios y templates). Luego, usted debe replicar los mismos pasos para los módulos **authorModule** y **editorialModule**.

## Módulo Inicial Book
Para la creación de un módulo en AngularJS, se deben definir una serie de archivos los cuales cumplen unas funciones específicas:

Nombre  | Función
------- | -------------
book.ctrl.js    | Controlador del módulo Book
book.mod.js     | Definición del módulo Book
book.svc.js     | Servicios del módulo Book
book.tpl.html   | Template del módulo Book

Usted debe crear los anteriores archivos del módulo **bookModule** en la ruta "src/modules/book". De igual manera cree los archivos para los módulos **authorModule** ruta "src/modules/author" y **editorialModule** ruta "src/modules/editorial". 

A continuación se explica con mayor detalle el funcionamiento de cada archivo, para este caso se hará con **Book**.

## book.mod.js
En este archivo se declara por primera vez el módulo "bookModule" y las dependencias que requiere. Para este caso tiene definido el módulo de "ui-bootstrap" y la declaración de la constante "bookContext" que contiene la Información del **api** del backend, en este caso es un String con la ruta "api/books".

```javascript
(function (ng) {
    var mod = ng.module("bookModule", ["ui.bootstrap"]);

    mod.constant("bookContext", "  api/books");

})(window.angular);
```
[Ir a book.mod.js](https://github.com/Uniandes-isis2603-201520/ejemplo-book/blob/paso2/bookstore-web/src/main/webapp/src/modules/book/book.mod.js).

De igual manera cree los archivos **editorial.mod.js** y **author.mod.js**. Tenga en cuenta el cambio del nombre de la constante que tiene la información de la url del api del módulo. Por ejemplo: Para autores ```mod.constant("authorContext", "  api/authors"); ```.

[Ir a author.mod.js](https://github.com/Uniandes-isis2603-201520/ejemplo-book/blob/paso2/bookstore-web/src/main/webapp/src/modules/author/author.mod.js),  [Ir a editorial.mod.js](https://github.com/Uniandes-isis2603-201520/ejemplo-book/blob/paso2/bookstore-web/src/main/webapp/src/modules/editorial/editorial.mod.js).

### Agregar archivo book.mod.js al index.html.

Para agregar el archivo book.mod.js usted debe agregar la siguiente línea de código dentro del tag ```<head>``` del archivo index.html.

```HTML
<script src="src/modules/book/book.mod.js" type="text/javascript"></script>
```

**Nota:** Para todos los _archivos.js_ que usted cree, recuerde registrarlos en el *index.html* tal como se muestra en el siguiente ejemplo:

```HTML
<script src="src/modules/book/book.mod.js" type="text/javascript"></script>
<script src="src/modules/author/author.mod.js" type="text/javascript"></script>
<script src="src/modules/editorial/editorial.mod.js" type="text/javascript"></script>
```

### Agregar librería ui.bootstrap

Para agregar la librería usted debe descargarla y agregarla en el directorio *resources/js*. Link de instrucciones: [Manual](
https://github.com/angular-ui/bootstrap#manual-download) y descargar el archivo **ui-bootstrap-tpls-0.13.4.min.js** para agregarlo en la carpeta js y referenciarlo en el index.html.

```HTML
<script src="resources/js/ui-bootstrap-tpls-0.13.4.min.js" type="text/javascript"></script>
```
**Nota:** El anterior paso se realiza sólo una vez.

## book.ctrl.js
Este archivo define el controlador del módulo, el controlador es un objeto Javascript encargado de gestionar el flujo de los datos de la vista y manejar los eventos. Entre los conceptos más importantes dentro de un controlador está el *scope*. El scope es un gran contenedor de datos, que transporta y hace visible la información necesaria para implementar la aplicación, desde el controlador a la vista y desde la vista al controlador. Para más información ir a [Documentación AngularJS- Controlador](https://docs.angularjs.org/tutorial/step_02).


El contenido del archivo book.ctrl.js se hará dentro de un **closure** con el fin de poder crear variables privadas. Para definir un controlador usted debe seguir los siguientes pasos:

- Declarar el modulo "bookModule" en la variable **mod**. Nota: Recuerde que el módulo bookModule fue declarado en el archivo book.mod.js y en ésta ocasión se está haciendo referencia al módulo ya creado.
```javascript
 var mod = ng.module("bookModule");
```
- Crear un controlador al modulo "bookModule", para esto usted debe declarar el controlador así:
```javascript
 var mod = ng.module("bookModule");
 mod.controller("bookctrl",["$scope","bookService", function($scope,svc){   
 }]);
```
 Donde el primer argumento es el nombre del controlador y luego se inyecta el scope(Para el manejo de DOM) y las dependencias a otros componentes necesarios en la lógica del controlador.

Para esta aplicación, el controlador se registra como "bookCtrl" y hace llamados a "bookService" para completar sus métodos. Principalmente, el controlador se compone de los mismos métodos que tiene el servicio (puesto que debe llamarlos según sea el caso) con la adición de otros métodos que permiten mostrar mensajes de alerta e información acerca de las operaciones que debe realizar el controlador.

### Métodos del controlador para desplegar mensajes

Los siguientes métodos describen la lógica necesaria para desplegar mensajes de alerta en el template "book.tpl.html". Recuerde que el módulo "BookModule". La lógica se compone de las siguientes variables y métodos o funciones:

- Variables para las alertas: Usted debe declarar la variable **$scope.alert** como un array. Aqui se almacena todas las alertas.
- Función **showMessage**: Recibe el mensaje en String y su tipo con el fin de almacenarlo en el array $scope.alerts.
- Función **showError**: Recibe un mensaje en String e invoca la función showMessage agregando el tipo "error" cómo parámetro.
- Función **showSuccess**: Recibe un mensaje en String e invoca la función showMessage agregando el tipo "success" cómo parámetro.
- Función **closeAlert**: Elimina una alerta de la colección de alertas disponibles.

```javascript
//Alertas
       $scope.alerts = [];
       this.closeAlert = function (index) {
           $scope.alerts.splice(index, 1);
       };

       function showMessage(msg, type) {
           var types = ["info", "danger", "warning", "success"];
           if (types.some(function (rc) {
               return type === rc;
           })) {
               $scope.alerts.push({ type: type, msg: msg });
           }
       }

       this.showError = function (msg) {
           showMessage(msg, "danger");
       };

       this.showSuccess = function (msg) {
           showMessage(msg, "success");
       };

       var self = this;
       function responseError(response) {
           self.showError(response.data);
       }
```

### Métodos del controlador para invocar servicios

Los siguientes métodos describen la lógica necesaria para comunicarse con el componente de servicio para book. Como se puede ver en el siguiente código, los métodos hacen referencia a operaciones CRUD (Create, Read, Update, Delete) las cuales hacen invocaciones al componente de servicios **"BookService - svc"** encargado de realizar el llamado al api.

```javascript
//Variables para el controlador
        this.readOnly = false;
        this.editMode = false;

        this.changeTab = function (tab) {
            $scope.tab = tab;
        };

        this.createRecord = function () {
            $scope.$broadcast("pre-create", $scope.currentRecord);
            this.editMode = true;
            $scope.currentRecord = {};
            $scope.$broadcast("post-create", $scope.currentRecord);
        };

        this.editRecord = function (record) {
            $scope.$broadcast("pre-edit", $scope.currentRecord);
            return svc.fetchRecord(record.id).then(function (response) {
                $scope.currentRecord = response.data;
                self.editMode = true;
                $scope.$broadcast("post-edit", $scope.currentRecord);
                return response;
            }, responseError);
        };

        this.fetchRecords = function () {
            return svc.fetchRecords().then(function (response) {
                $scope.records = response.data;
                $scope.currentRecord = {};
                self.editMode = false;
                return response;
            }, responseError);
        };
        this.saveRecord = function () {
            return svc.saveRecord($scope.currentRecord).then(function () {
                self.fetchRecords();
            }, responseError);
        };
        this.deleteRecord = function (record) {
            return svc.deleteRecord(record.id).then(function () {
                self.fetchRecords();
            }, responseError);
        };
        this.fetchRecords();
```

Al final de la definición de los métodos que se comunican con los servicios, se realiza el llamado al método **this.fetchRecords** con el ánimo de realizar una primera carga de todos los libros y mostrarlos en el template book.tpl.html.

**Nota:** Recuerde agregar el archivo book.ctrl.js al index.html respetando el orden de importación. *Por ejemplo:* El script del controlador debe estar después del script que carga el archivo **book.mod.js**.

[Ir a book.ctrl.js](https://github.com/Uniandes-isis2603-201520/ejemplo-book/blob/paso2/bookstore-web/src/main/webapp/src/modules/book/book.ctrl.js).


Una vez definido el controlador para el módulo Book, usted debe replicar los anteriores pasos con los módulos author y editorial con el fin de crear un controlador para cada módulo. Como los controladores en éste momento tienen las misma lógica los archivos book.ctrl, author.ctrl y editorial.ctrl van ser muy parecidos.

[Ir a author.ctrl.js](https://github.com/Uniandes-isis2603-201520/ejemplo-book/blob/paso2/bookstore-web/src/main/webapp/src/modules/author/author.ctrl.js),  [Ir a editorial.ctrl.js](https://github.com/Uniandes-isis2603-201520/ejemplo-book/blob/paso2/bookstore-web/src/main/webapp/src/modules/editorial/editorial.ctrl.js)


## book.svc.js
En este archivo se define el componente de servicio "bookService" en el cual se crean los métodos de la tabla 2. Estos métodos serán los encargados de realizar las peticiones HTTP(GET-POST-DELETE-PUT) al api. **Nota:** *Cuando se ha registado el módulo mocksModule en la aplicación principal, los siguientes métodos son ignorados.*

Nombre          | Función                                                                       | Tipo de petición
--------------- | ----------------------------------------------------------------------------- | -------------------
fetchRecords    | Retorna todos los libros.                                                      | GET
fetchRecord     | Retorna el libro que se pasa como parámetro.                                   | GET
saveRecord      | Si el registro definido como parámetro a la función "saveRecord" existe, la función actualiza la información del libro ya existente.                                        | PUT
saveRecord      | En caso de que el registro definido como parámetro a la función "saveRecord" se procede a crear un nuevo book haciendo una petición de creación de registro.                                                                           | POST
deleteRecord    | Hace una petición DELETE para borrar el libro que se pasa como parámetro      | DELETE
**Tabla 2.**

Para solicitar datos al backend usted debe utilizar el servicio de angular $http, el cuál ofrece una serie de métodos enfocados en realizar las operaciones típicas implementadas dentro del protocolo HTTP. Por ejemplo, para enviar datos post disponemos de $http.post(). En ese método se puede enviar como parámetro, aparte de la URL del servicio, un objeto con los datos que se desean enviar a éste. Se recomienda leer la documentación del servicio $http. [Ir a documentación de $http.](https://docs.angularjs.org/api/ng/service/$http)

A continuación, se describen los métodos implementados en el archivo book.svc.js para realizar peticiones al backend.

```javascript

        this.fetchRecords = function () {
            return $http.get(context);
        };

        /**
         * Obtener un registro de books.
         * Hace una petición GET a /books/:id para obtener
         * los datos de un registro específico de books
         * @param {number} id del registro a obtener
         * @returns {promise} promise para leer la respuesta del servidor
         * Devuelve un objeto de books con sus atributos y reviews
         */
        this.fetchRecord = function (id) {
            return $http.get(context + "/" + id);
        };

        /**
         * Guardar un registro de books.
         * Si currentRecord tiene la propiedad id, hace un PUT a /books/:id con los
         * nuevos datos de la instancia de books.
         * Si currentRecord no tiene la propiedad id, se hace un POST a /books
         * para crear el nuevo registro de books
         * @param {object} currentRecord instancia de book a guardar/actualizar
         * @returns {promise} promise para leer la respuesta del servidor
         * Devuelve un objeto de books con sus datos incluyendo el id
         */
        this.saveRecord = function (currentRecord) {
            if (currentRecord.id) {
                return $http.put(context + "/" + currentRecord.id, currentRecord);
            } else {
                return $http.post(context, currentRecord);
            }
        };

        /**
         * Hace una petición DELETE a /books/:id para eliminar un book
         * @param {number} id identificador de la instancia de book a eliminar
         * @returns {promise} promise para leer la respuesta del servidor
         * No devuelve datos.
         */
        this.deleteRecord = function (id) {
            return $http.delete(context + "/" + id);
        };

```
**Nota:** Recuerde agregar el archivo book.svc.js al index.html respetando el orden de importación. *Por ejemplo:* El script del servicio debe estar después del script que carga el archivo **book.ctrl.js**. De esta manera el index.html debe lucir muy parecido a:

```HTML
<script src="resources/js/ui-bootstrap-tpls-0.13.4.min.js" type="text/javascript"></script>
<script src="src/modules/book/book.mod.js"></script>
<script src="src/modules/book/book.ctrl.js"></script>
<script src="src/modules/book/book.svc.js"></script>
```
[Ir a book.svc.js](https://github.com/Uniandes-isis2603-201520/ejemplo-book/blob/paso2/bookstore-web/src/main/webapp/src/modules/book/book.svc.js)

Usted debe replicar los anteriores pasos con los módulos author y editorial con el fin de crear un servicio para cada módulo. Como los servicios en éste momento tienen las misma lógica los archivos book.svc, author.svc y editorial.svc van ser muy parecidos.

[Ir a author.svc.js](https://github.com/Uniandes-isis2603-201520/ejemplo-book/blob/paso2/bookstore-web/src/main/webapp/src/modules/author/author.svc.js), [Ir a editorial.svc.js](https://github.com/Uniandes-isis2603-201520/ejemplo-book/blob/paso2/bookstore-web/src/main/webapp/src/modules/editorial/editorial.svc.js).

## Comportamiento dinámico en el template book.tpl.html
Ahora usted debe modificar el archivo book.tpl.html con el fin de adaptarlo al funcionamiento de AngularJS y así manejar los datos de la aplicación.

Book.tpl.html empieza con el siguiente div: ```<div id="book-header">``` el cual muestra el menú del apartado de libros, en donde se encuentran los botones que permiten crear un libro, refrescar los libros mostrados, guardar los cambios realizados y cancelar la operación actual. Estos botones son mostrados u ocultados según la operación que se este realizando (haciendo uso de ng-show y ng-hide de Angular).

A continuación se hace uso de la directiva ```<alert>``` de ui-boostrap ([Alert-ui-bootstrap](https://angular-ui.github.io/bootstrap/)) mediante el cual se despliegan mensajes de información relevante para el usuario, como la recepción exitosa del formulario que ha enviado y validaciones de campos.
Usted pueded agregar la directiva alert en cualquier parte de su plantilla book.tpl.html. Para el ejemplo se aconseja la parte superior de la plantilla.
```HTML
<alert ng-repeat="alert in alerts" type="{{alert.type}}" close="ctrl.closeAlert($index)">{{alert.msg}}</alert>
```
Donde *alerts* es el arreglo definido en el controlador **bookCtrl** , de esta manera en el DOM se graficarán solamente las alertas almacenadas en dicho arreglo gracias al comando de AngularJS [ng-repeat](https://docs.angularjs.org/api/ng/directive/ngRepeat) el cual repite una porción de código html de acuerdo a la cantidad de registros presentes en el arreglo **alerts**. Por defecto el controlador carga una alerta de tipo información con un mensaje de bienvenida.

Posteriormente,  existe un tag ```<div ng-hide="ctrl.editMode">``` el cual muestra la información de los libros como: el nombre, una descripción, autor,Isbn y fecha de publicación cuando la variable **ctrl.editMode** es falsa. Seguido hay otro tag ```<div ng-show="ctrl.editMode" class="well">``` el cuál muestra el formulario usado para la creación o edición de libros y sólo estará visible si la variable *crtl.editMode* es verdadera. Observe que según la anterior lógica nunca van a estar los anteriores **tag** visibles al mismo tiempo. El formulario consta de una alerta personalizada que despliega un mensaje si los campos requeridos aún no están completos. Para la implementación de esta alerta se usa el módulo ngMessages de angular el cual lo puede descargar [aquí](https://code.angularjs.org/1.4.8/). **Nota:** Descargar el archivo minificado angular-messages.min.js, copiarlo al directorio resources/js/*, registrarlo en el index.html e inyectar la dependencia en el modulo principal archivo app.js.   

[Ir a book.tpl.html](https://github.com/Uniandes-isis2603-201520/ejemplo-book/blob/paso2/bookstore-web/src/main/webapp/src/modules/book/book.tpl.html).


Usted debe replicar los anteriores pasos con los módulos author y editorial con el fin de crear un template para cada módulo. Cada template es diferente a los otros. Por ejemplo: el módulo book tiene asociado un modelo de datos con nombre, isbn y fecha de publicación mientras el author tiene asociado los atributos de nombre y fecha de nacimiento; por lo tanto, cada template debe estar acorde al modelo de datos del módulo. Ver archivos **author.tpl.html** y **editorial.tpl.html**.

[Ir a author.tpl.html](https://github.com/Uniandes-isis2603-201520/ejemplo-book/blob/paso2/bookstore-web/src/main/webapp/src/modules/author/author.tpl.html), [Ir a editorial.tpl.html](https://github.com/Uniandes-isis2603-201520/ejemplo-book/blob/paso2/bookstore-web/src/main/webapp/src/modules/editorial/editorial.tpl.html).

## Configuración del módulo bookModule en el app.js

En el archivo *app.js* se debe cargar los módulos creados *bookModule*, *authorModule* y *editorialModule* (definidos anteriormente), para ello se modifica la variable *mainApp* para incluir los módulos en su colección.

```javascript
var mainApp = ng.module('mainApp', [
    //'ngCrudMock',
    'bookModule',
    'authorModule',
    'editorialModule',
    'ui.router'
]);
```

Luego, configure el provider encargado de establecer la navegación con ui-router ($stateProvider) y agregue un nuevo estado "book" ```state("book")``` con el fin de introducir el nuevo estado "book" en la navegación de la aplicación. Observe que en cada estado usted debe definir la url, la url del template a desplegar, el contralador asociado y un alias del mismo. Ver ejemplo:

```javascript
.state('book', {
              url: '/book',
              templateUrl: tplUrl,
              controller: 'bookCtrl',
              controllerAs: alias})
```

De igual manera, cree los estados 'editorial' y 'author' en el provider ui-router definiendo sus características.

```javascript
.state('editorial', {
                        url: '/editorial',
                        controller: "editorialCtrl",
                        controllerAs: "ctrl",
                        templateUrl: "src/modules/editorial/editorial.tpl.html"
                    })
                    .state('author', {
                        url: '/author',
                        controller: "authorCtrl",
                        controllerAs: "ctrl",
                        templateUrl: "src/modules/author/author.tpl.html"
                    });
```


## Módulos mocks.

Los mocks son objetos en javascript que nos permiten simular el comportamiento de un api. Esto con el fin de trabajar en el frontEnd independientemente de que todos los servicios del api en JavaEE estén desarrollados o no. En éste caso se realizará la implementación de varios mocks para los módulos Book, author y editorial haciendo uso del módulo de AngularJS **ngMockE2E**. Para activar los mocks en su aplicación AngularJS realice los siguientes pasos:

- Descargar el módulo o libreria **ngMockE2E** en el directorio /resources/js/angular-mocks.js al igual que la libreria ui.bootstrap. [Link de descarga](https://code.angularjs.org/1.4.9/angular-mocks.js). **Nota:** No olvide registrarlo en el index.html.
- Crear el archivo book.mock.js en la ubicación src/modules/book/
- En el archivo:
  - Crear módulo mockModule e inyectar módulo ngMockE2E.

  ```javascript
    var mod = ng.module('mockModule', ['ngMockE2E']);
    ]);
  ```
  - Definir variables y expresiones regulares dentro de la configuración de ejecución del módulo.

```javascript
    mod.run(['$httpBackend', function ($httpBackend) {
      var ignore_regexp = new RegExp('^((?!api).)*$');// Exp reg diferente a /api/
      /*
        * @type RegExp
        * recordUrl acepta cualquier url con el formato
        * api/(cualquierpalabra)/(numero)
        * ej: api/books/1
        */
       var recordUrl = new RegExp('api/books/([0-9]+)');
       /*
        * @type Array
        * records: Array con un libro por defecto
        */
       var records = [{
               id: 1,
               name: 'Libro1',
               description: 'Libro Mock',
               isbn: '12345-1',
               image: 'http://unlibrocadadia.es/wp-content/uploads/2013/05/La_nieve_del_almirante_alvaro_mutis.jpg',
               publishDate: '22-January-2016'
           }];
        function getQueryParams(url) {
            var vars = {}, hash;
            var hashes = url.slice(url.indexOf('?') + 1).split('&');
            for (var i = 0; i < hashes.length; i++) {
                hash = hashes[i].split('=');
                vars[hash[0]] = hash[1];
            }
            return vars;
        }
    ]);
  ```

  - Definir las acciones a relizar cada vez que se hacen peticiones GET-POST-DELETE-PUT en ambiente de ejecución.
    Ejemplo para las peticiones GET a "api/books".
  ```javascript
  /*
       * Ignora las peticiones GET, no contempladas en la Exp regular ignore_regexp
       */
      $httpBackend.whenGET(ignore_regexp).passThrough();

          /*
     * Esta funcion se ejecuta al invocar una solicitud GET a la url "api/books"
     * La funcion obtiene los parámetros de consulta "queryParams" para establecer
     * la pagina y la maxima cantida de records. Con los anteriores parametros
     * se realiza la simulacion de la paginacion.
     * Response: 200 -> Status ok, array de libros y los headers.
     */

      $httpBackend.whenGET('api/books').respond(function (method, url) {
          var queryParams = getQueryParams(url);
          var responseObj = [];
          var page = queryParams.page;
          var maxRecords = queryParams.maxRecords;
          var headers = {};
          if (page && maxRecords) {
              var start_index = (page - 1) * maxRecords;
              var end_index = start_index + maxRecords;
              responseObj = records.slice(start_index, end_index);
              headers = {"X-Total-Count": records.length};
          } else {
              responseObj = records;
          }
          return [200, responseObj, headers];
      });
    ]);
  ```

  Para las peticiones POST-PUT-DELETE:

```javascript
          /*
         * Esta función se ejecuta al invocar una solicitud GET a la url "api/books/[numero]"
         * Obtiene el id de la url y obtiene el registro asociado dentro del array records.
         * Response: 200 -> Status ok, record -> libro y ningún header.
         */
          $httpBackend.whenGET(recordUrl).respond(function (method, url) {
              var id = parseInt(url.split('/').pop());
              var record;
              ng.forEach(records, function (value) {
                  if (value.id === id) {
                      record = ng.copy(value);
                  }
              });
              return [200, record, {}];
          });    
          /*
           * Esta función se ejecuta al invocar una solicitud POST a la url "api/books"
           * Obtiene el record de libro desde el cuerpo de la peticion
           * Genera un id aleatorio y lo asocia al record de libro y lo guarda en el
           * array de records.
           * Response: 201 -> Status created, record -> libro y ningún header.
           */
          $httpBackend.whenPOST('api/books').respond(function (method, url, data) {
              var record = ng.fromJson(data);
              record.id = Math.floor(Math.random() * 10000);
              records.push(record);
              return [201, record, {}];
          });
         /*
         * Esta funcion se ejecuta al invocar una solicitud DELETE a la url "api/books/[numero]"
         * Obtiene el id del la url y el registro asociado el registro asociado
         * dentro del array records.
         * Luego realiza un [splice](http://www.w3schools.com/jsref/jsref_splice.asp) "eliminar registro del array".
         * Response: 204, no retorna ningun dato ni headers.
         */

          $httpBackend.whenDELETE(recordUrl).respond(function (method, url) {
              var id = parseInt(url.split('/').pop());
              ng.forEach(records, function (value, key) {
                  if (value.id === id) {
                      records.splice(key, 1);
                  }
              });
              return [204, null, {}];
          });

          /*
           * Esta funcion se ejecuta al invocar una solicitud PUT a la url "api/books/[numero]"
           * Obtiene el id del la url y el record de libro desde el cuerpo de la peticion
           * Busca y reemplaza el anterior registro por el enviado en el cuerpo de la solicitud
           * Response: 204, no retorna ningun dato ni headers.
           *
           */
          $httpBackend.whenPUT(recordUrl).respond(function (method, url, data) {
              var id = parseInt(url.split('/').pop());
              var record = ng.fromJson(data);
              ng.forEach(records, function (value, key) {
                  if (value.id === id) {
                      records.splice(key, 1, record);
                  }
              });
              return [204, null, {}];
          });


  ```
El archivo completo lo puede encontrar en la siguiente ruta:
[Ir a book.mocks.js](https://github.com/Uniandes-isis2603-201520/ejemplo-book/blob/paso2/bookstore-web/src/main/webapp/src/modules/mocks/book.mock.js)

  - Inyectar módulo "mockModule" en el módulo principal. Archivo app.js.

  ```javascript
  var mod = ng.module("mainApp", [
      "ui.router",
      "bookModule",
      "bookMock"
    ]);
  ```
  Nota: Recuerde que usted debe registrar el archivo book.mock.js en el index.html de su proyecto.

  ```javascript
  <head>
  ...
  <script src="src/modules/book/book.mock.js" type="text/javascript"></script>
  </head>
  ```
 
Repita los anteriores pasos para definir un módulo mock para author y editorial. Tenga en cuenta que cada mock tiene una única declaración de url y los atributos de la variable **records** de cada mock  cambian respecto al módelo. Por ejemplo: el mock de author debe tener la la url **recordUrl** definida así: 
```javascript
var recordUrl = new RegExp('api/authors/([0-9]+)');
``` 
y los atributos de cada elemento del array **record** son:
```javascript
        /*
         * @type Array
         * records: Array con un Author por defecto
         */
        var records = [{
                id: 1,
                name: 'Alvaro Mutis',
                birthDate: '1940-01-22'
            }];
```

Al terminar la anterior guía usted debe tener una aplicación de AngularJS con un controlador, un módulo, un archivo de servicios, un archivo de mocks y un template dinámico para los módulos book, editorial y author. Usted debe poder crear, editar, leer y borrar en memoria libros, autores y editoriales de la aplicación.

