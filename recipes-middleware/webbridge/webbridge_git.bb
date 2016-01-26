SUMMARY = "Metrological's webbridge middleware"
HOMEPAGE = "http://www.metrological.com/"
SECTION = "metrological"
LICENSE = "CLOSED"

DEPENDS = "zlib cppsdk"

PV = "1.0+gitr${SRCPV}"

SRC_URI = "git://git@github.com/Metrological/webbridge.git;protocol=ssh"

SRCREV = "c54cea9a54485577ca10759810a1080119abf6cb"

S = "${WORKDIR}/git"

inherit pkgconfig cmake
 
PACKAGECONFIG ??= "web-ui delayedresponse deviceinfo fancontrol fileserver i2ccontrol provisioning spicontrol systeminformer tempcontol tracecontrol webproxy dailserver webkitbrowser"

PACKAGECONFIG[debug]              = "-DWEBBRIDGE_DEBUG=ON,-DWEBBRIDGE_DEBUG=OFF,"
PACKAGECONFIG[web-ui]             = "-DWEBBRIDGE_WEB_UI=ON,-DWEBBRIDGE_WEB_UI=OFF,"
PACKAGECONFIG[browser]            = "-DWEBBRIDGE_PLUGIN_BROWSER=ON,-DWEBBRIDGE_PLUGIN_BROWSER=OFF,"
PACKAGECONFIG[delayedresponse]    = "-DWEBBRIDGE_PLUGIN_DELAYEDRESPONSE=ON,-DWEBBRIDGE_PLUGIN_DELAYEDRESPONSE=OFF,"
PACKAGECONFIG[deviceinfo]         = "-DWEBBRIDGE_PLUGIN_DEVICEINFO=ON,-DWEBBRIDGE_PLUGIN_DEVICEINFO=OFF,"
PACKAGECONFIG[fancontrol]         = "-DWEBBRIDGE_PLUGIN_FANCONTROL=ON,-DWEBBRIDGE_PLUGIN_FANCONTROL=OFF,"
PACKAGECONFIG[fileserver]         = "-DWEBBRIDGE_PLUGIN_FILESERVER=ON,-DWEBBRIDGE_PLUGIN_FILESERVER=OFF,"
PACKAGECONFIG[i2ccontrol]         = "-DWEBBRIDGE_PLUGIN_I2CCONTROL=ON,-DWEBBRIDGE_PLUGIN_I2CCONTROL=OFF,"
PACKAGECONFIG[provisioning]       = "-DWEBBRIDGE_PLUGIN_PROVISIONING=ON,-DWEBBRIDGE_PLUGIN_PROVISIONING=OFF,dxdrm"
PACKAGECONFIG[remotecontrol]      = "-DWEBBRIDGE_PLUGIN_REMOTECONTROL=ON,-DWEBBRIDGE_PLUGIN_REMOTECONTROL=OFF,"
PACKAGECONFIG[spicontrol]         = "-DWEBBRIDGE_PLUGIN_SPICONTROL=ON,-DWEBBRIDGE_PLUGIN_SPICONTROL=OFF,"
PACKAGECONFIG[surfacecompositor]  = "-DWEBBRIDGE_PLUGIN_SURFACECOMPOSITOR=ON,-DWEBBRIDGE_PLUGIN_SURFACECOMPOSITOR=OFF,"
PACKAGECONFIG[systeminformer]     = "-DWEBBRIDGE_PLUGIN_SYSTEMINFORMER=ON,-DWEBBRIDGE_PLUGIN_SYSTEMINFORMER=OFF,"
PACKAGECONFIG[tempcontol]         = "-DWEBBRIDGE_PLUGIN_TEMPCONTROL=ON,-DWEBBRIDGE_PLUGIN_TEMPCONTROL=OFF,"
PACKAGECONFIG[tracecontrol]       = "-DWEBBRIDGE_PLUGIN_TRACECONTROL=ON,-DWEBBRIDGE_PLUGIN_TRACECONTROL=OFF,"
PACKAGECONFIG[webproxy]           = "-DWEBBRIDGE_PLUGIN_WEBPROXY=ON,-DWEBBRIDGE_PLUGIN_WEBPROXY=OFF,"
PACKAGECONFIG[wificontrol]        = "-DWEBBRIDGE_PLUGIN_WIFICONTROL=ON,-DWEBBRIDGE_PLUGIN_WIFICONTROL=OFF,"
PACKAGECONFIG[dailserver]         = "-DWEBBRIDGE_PLUGIN_DIALSERVER=ON,-DWEBBRIDGE_PLUGIN_DIALSERVER=OFF,"
PACKAGECONFIG[webkitbrowser]      = "-DWEBBRIDGE_PLUGIN_WEBKITBROWSER=ON,-DWEBBRIDGE_PLUGIN_WEBKITBROWSER=OFF,wpe"

EXTRA_OECMAKE = "\
    -DINSTALL_HEADERS_TO_TARGET=ON \
"

FILES_${PN}-dbg += "/usr/share/webbridge/*/.debug"
