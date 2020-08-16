module.exports = {
    publicPath: '/',
    pwa: {
        name: 'Old Vehicles',
        manifestOptions: {
            name: "Old Vehicles",
            display: "standalone",
            scope: "/",
            start_url: "/"
        },
        // workboxPluginMode: 'GenerateSW',
        workboxOptions: {
            navigateFallback: '/index.html',
            runtimeCaching: [{
                urlPattern: new RegExp('^https'),
                handler: 'NetworkFirst',
                options: {
                    networkTimeoutSeconds: 2,
                    cacheName: 'api-cache',
                    cacheableResponse: {
                        statuses: [200],
                    },
                },
            }]
        }
    }
};