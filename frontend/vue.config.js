module.exports = {
    publicPath: '/',
    pwa: {
        name: 'Old Vehicles',
        manifestOptions: {
            name: "Old Vehicles",
            display: "standalone",
            scope: "/",
            start_url: "/",
            theme_color: "#617D89",
            background_color: "#212121"
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