# linux-pocketbeagle.bb:
#
#   Based on meta-skeleteon/recipes-kernel/linux/linux-yocto-custom.bb
#
#   An example kernel recipe that uses the linux-yocto and oe-core
#   kernel classes to apply a subset of yocto kernel management to git
#   managed kernel repositories.
#
#   To use linux-yocto-custom in your layer, copy this recipe (optionally
#   rename it as well) and modify it appropriately for your machine. i.e.:
#
#   COMPATIBLE_MACHINE_yourmachine = "yourmachine"
#
#   You must also provide a Linux kernel configuration. The most direct
#   method is to copy your .config to files/defconfig in your layer,
#   in the same directory as the copy (and rename) of this recipe and
#   add file://defconfig to your SRC_URI.
#
#   To use the yocto kernel tooling to generate a BSP configuration
#   using modular configuration fragments, see the yocto-bsp and
#   yocto-kernel tools documentation.
#
# Warning:
#
#   Building this example without providing a defconfig or BSP
#   configuration will result in build or boot errors. This is not a
#   bug.
#
#
# Notes:
#
#   patches: patches can be merged into to the source git tree itself,
#            added via the SRC_URI, or controlled via a BSP
#            configuration.
#
#   defconfig: When a defconfig is provided, the linux-yocto configuration
#              uses the filename as a trigger to use a ’allnoconfig’ baseline
#              before merging the defconfig into the build.
#
#              If the defconfig file was created with make_savedefconfig,
#              not all options are specified, and should be restored with their
#              defaults, not set to ’n’. To properly expand a defconfig like
#              this, specify: KCONFIG_MODE="--alldefconfig" in the kernel
#              recipe.
#
#   example configuration addition:
#            SRC_URI += "file://smp.cfg"
#   example patch addition (for kernel v4.x only):
#            SRC_URI += "file://0001-linux-version-tweak.patch"
#   example feature addition (for kernel v4.x only):
#            SRC_URI += "file://feature.scc"
#
inherit kernel
require recipes-kernel/linux/linux-stable.inc

# Override SRC_URI in a copy of this recipe to point at a different source
# tree if you do not want to build from Linus’ tree.
SRC_URI = "\
	git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux-stable.git;protocol=git;nocheckout=1;name=stable \
	file://defconfig \
	file://0001-Stripped-back-pocketbeagle-devicetree.patch \
"

LINUX_VERSION ?= "5.4"
LINUX_VERSION_EXTENSION_append = "-pocketbeagle"

# Modify SRCREV to a different commit hash in a copy of this recipe to
# build a different release of the Linux kernel.
# tag: v4.14.18 81d0cc85caabe062991ea45ddada814835d47fb0
SRCREV_stable="bdc3a8f6a8e8b798c46683a98b97d52b3a5708e4"

PV = "5.4.66"

# Override COMPATIBLE_MACHINE to include your machine in a copy of this recipe file.

COMPATIBLE_MACHINE = "pocketbeagle"
