SUMMARY = "Web Platform for Embedded Framework"
HOMEPAGE = "https://github.com/WebPlatformForEmbedded"
SECTION = "wpe"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

DEPENDS = "zlib"
DEPENDS_append_libc-musl = " libexecinfo"

PV = "3.0+gitr${SRCPV}"

SRC_URI = "git://git@github.com/WebPlatformForEmbedded/WPEFramework.git;protocol=ssh;branch=master \
		   file://wpeframework-init \
		   file://wpeframework.service.in \
"
SRCREV = "3da421cf6407dd26360f36222ed10f474b7d65e1"

S = "${WORKDIR}/git"

inherit cmake pkgconfig

PACKAGECONFIG ?= ""

PACKAGECONFIG[cyclicinspector] 	= "-DWPEFRAMEWORK_TEST_CYCLICINSPECTOR=ON,-DWPEFRAMEWORK_TEST_CYCLICINSPECTOR=OFF,"
PACKAGECONFIG[debug] 			= "-DCMAKE_BUILD_TYPE=Debug,-DCMAKE_BUILD_TYPE=Release,"
PACKAGECONFIG[provisionproxy] 	= "-DWPEFRAMEWORK_PROVISIONPROXY=ON,-DWPEFRAMEWORK_PROVISIONPROXY=OFF,libprovision (>= 2.0)"
PACKAGECONFIG[virtualinput] 	= "-DWPEFRAMEWORK_VIRTUALINPUT=ON,-DWPEFRAMEWORK_VIRTUALINPUT=OFF,"

EXTRA_OECMAKE += " \
    -DINSTALL_HEADERS_TO_TARGET=ON \
"

do_install_append() {
    #rm -r ${D}/${datadir}/WPEFramework/cmake

    if ${@bb.utils.contains("DISTRO_FEATURES", "systemd", "true", "false", d)}
    then
        if ${@bb.utils.contains("MACHINE_FEATURES", "platformserver", "true", "false", d)}
        then
           extra_after=""
        elif ${@bb.utils.contains("PREFERRED_PROVIDER_virtual/egl", "broadcom-refsw", "true", "false", d)}
        then
           extra_after="nxserver.service"
        fi
        extra_after="${extra_after} ${WAYLAND_COMPOSITOR}"
        install -d ${D}${systemd_unitdir}/system
        sed -e "s|@EXTRA_AFTER@|${extra_after}|g" < ${WORKDIR}/wpeframework.service.in > ${D}${systemd_unitdir}/system/wpeframework.service
    else
        install -d ${D}${sysconfdir}/init.d
        install -m 0755 ${WORKDIR}/wpeframework-init ${D}${sysconfdir}/init.d/wpeframework
    fi
}

# ----------------------------------------------------------------------------

PACKAGES =+ "${PN}-initscript"

FILES_${PN}-initscript = "${sysconfdir}/init.d/wpeframework"

# libinterfaces.so and libproxystubs.so are installed in /usr/lib/webbridge/proxystubs
# Fixme: to avoid the need for packaging workarounds, plug-ins should be installed
# one level below /usr/lib (e.g. /usr/lib/webbridge-proxystubs)
#FILES_${PN}-dbg += "${libdir}/wpeframework/proxystubs/.debug"

FILES_SOLIBSDEV = ""
FILES_${PN} += "${libdir}/*.so ${datadir}/WPEFramework/*"

# ----------------------------------------------------------------------------

INITSCRIPT_PACKAGES = "${PN}-initscript"
INITSCRIPT_NAME_${PN}-initscript = "wpeframework"
INITSCRIPT_PARAMS_${PN}-initscript = "defaults 80 24"

RRECOMMENDS_${PN} = "${PN}-initscript"

# ----------------------------------------------------------------------------

INSANE_SKIP_${PN} += "dev-so"
INSANE_SKIP_${PN}-dbg += "dev-so"

TOOLCHAIN = "gcc"
