var app = angular.module('monitoring', ['ui.bootstrap', 'ngRoute', 'ui.grid', 'ui.grid.resizeColumns']);

app.config(function ($routeProvider, $httpProvider) {

    $routeProvider.when('/view', {
        templateUrl: 'view/manager.html',
        controller: 'view'
    }).when('/login', {
        templateUrl: 'login.html',
        controller: 'login'
    }).otherwise('/login');

    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
});
app.controller('view', function ($scope, $interval, $document, $http, $timeout, uiGridConstants, $rootScope, $location) {
    $scope.updateInterval = 1;
    $scope.grids = {}
    $scope.grids["deposit"] = {
        currentPage: 1,
        totalItems: 0,
        maxSize: 15,
        begin: 0,
        end: 0,
        gridOptions: {
            enableColumnResizing: true,
            onRegisterApi: function (gridApi) {
                // dirty hack for column auto resizing (double click to all resizers)
                $timeout(function (gridApi) {
                    var resizers = document.getElementsByClassName('ui-grid-column-resizer');
                    for (var i = 0; i < (resizers.length - 2); i++) {
                        var event = new MouseEvent('dblclick', {
                            'view': window,
                            'bubbles': true,
                            'cancelable': true
                        });
                        resizers[i].dispatchEvent(event);
                    }
                }, 1000, true, gridApi);

                // dirty hack for column auto resizing (resize last column if horizontal scroll present)
                $timeout(function (gridApi) {
                    var scrollWidth = document.getElementsByClassName('ui-grid-viewport')[0].scrollWidth;
                    if (scrollWidth > 850) {
                        var resizers = document.getElementsByClassName('ui-grid-column-resizer');
                        var event = new MouseEvent('dblclick', {
                            'view': window,
                            'bubbles': true,
                            'cancelable': true
                        });
                        resizers[resizers.length - 2].dispatchEvent(event);
                    }
                }, 1000, true, gridApi);
            }
        }
    }
    $scope.grids["deposit"].gridOptions.enableHorizontalScrollbar = uiGridConstants.scrollbars.WHEN_NEEDED;
    $scope.grids["deposit"].gridOptions.enableVerticalScrollbar = uiGridConstants.scrollbars.WHEN_NEEDED;
    $scope.grids["deposit"].gridOptions.enableColumnResizing = true;

    $scope.grids["transfer"] = {
        currentPage: 1,
        totalItems: 0,
        maxSize: 15,
        begin: 0,
        end: 0,
        gridOptions: {}
    }
    $scope.grids["transfer"].gridOptions.enableHorizontalScrollbar = uiGridConstants.scrollbars.WHEN_NEEDED;
    $scope.grids["transfer"].gridOptions.enableVerticalScrollbar = uiGridConstants.scrollbars.WHEN_NEEDED;
    $scope.grids["transfer"].gridOptions.enableColumnResizing = true;

    $scope.grids["withdrawal"] = {
        currentPage: 1,
        totalItems: 0,
        maxSize: 15,
        begin: 0,
        end: 0,
        gridOptions: {}
    }
    $scope.grids["withdrawal"].gridOptions.enableHorizontalScrollbar = uiGridConstants.scrollbars.WHEN_NEEDED;
    $scope.grids["withdrawal"].gridOptions.enableVerticalScrollbar = uiGridConstants.scrollbars.WHEN_NEEDED;
    $scope.grids["withdrawal"].gridOptions.enableColumnResizing = true;


    $scope.numPerPage = 10;


    $scope.refresh = function () {
        $scope.loadData($scope.grids["deposit"].currentPage - 1, $scope.numPerPage, "deposit");
        $scope.loadData($scope.grids["transfer"].currentPage - 1, $scope.numPerPage, "transfer");
        $scope.loadData($scope.grids["withdrawal"].currentPage - 1, $scope.numPerPage, "withdrawal");
    }

    $scope.generate = function () {
        $http.post('generate', {}).success(function () {
        }).error(function (data) {
            $rootScope.authenticated = false;
        });
    }


    $scope.loadData = function (begin, end, type) {
        $http.get('api/v1.0/resource/totalItems/' + type).success(function (data) {
            $scope.grids[type].totalItems = data;
        }).error(function (data) {
            $rootScope.authenticated = false;
            $location.path("/login");
        });
        $http.get('api/v1.0/resource/' + begin + '/' + end + '/' + type).success(function (data) {
            $scope.grids[type].gridOptions.data = data;
        }).error(function (data) {
            $rootScope.authenticated = false;
            $location.path("/login");
        });
    }

    $interval(function () {
        $scope.refresh();
    }, $scope.updateInterval * 60 * 1000);

    $scope.$watch('grids[\'deposit\'].currentPage + numPerPage', function () {
        $scope.loadData($scope.grids["deposit"].currentPage - 1, $scope.numPerPage, "deposit");
    })
    $scope.$watch('grids[\'transfer\'].currentPage  + numPerPage', function () {
        $scope.loadData($scope.grids["transfer"].currentPage - 1, $scope.numPerPage, "transfer");
    })
    $scope.$watch('grids[\'withdrawal\'].currentPage  + numPerPage', function () {
        $scope.loadData($scope.grids["withdrawal"].currentPage - 1, $scope.numPerPage, "withdrawal");
    })


});


app.controller('error', function ($rootScope, $scope, $http, $location) {
    $rootScope.authenticated = false;

});

app.controller('login', function ($rootScope, $scope, $http, $location) {
    $rootScope.authenticated = false;
    $scope.credentials = {};

    var authenticate = function (credentials, callback) {
        var headers = credentials ? {
            authorization: "Basic "
            + btoa(credentials.username + ":" + credentials.password)
        } : {};

        $http.get('user', {headers: headers}).success(function (data) {
            if (data.name) {
                $rootScope.authenticated = true;
            } else {
                $rootScope.authenticated = false;
            }
            callback && callback();
        }).error(function () {
            $rootScope.authenticated = false;
            callback && callback();
        });
    }

    $scope.login = function () {
        authenticate($scope.credentials, function () {
            if ($rootScope.authenticated) {
                $location.path("/view");
                $scope.error = false;
            } else {
                $location.path("/login");
                $scope.error = true;
            }
        });
    };
});

app.controller('navigation', function ($rootScope, $scope, $http, $location) {

    $scope.logout = function () {
        $http.post('logout', {}).success(function () {
            $rootScope.authenticated = false;
            $location.path("/login");
        }).error(function (data) {
            $rootScope.authenticated = false;
        });
    }

});