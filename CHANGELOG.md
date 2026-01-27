# Changelog

## [1.3.1](https://github.com/Suezenv/email-otp-authenticator/compare/v1.3.0...v1.3.1) (2026-01-27)


### Features

* **messages:** add info messages ([d45576c](https://github.com/Suezenv/email-otp-authenticator/commit/d45576c7c1154a33dd887183b1bde350ce4dd73f))


### Miscellaneous Chores

* release 1.3.1 ([11bf82b](https://github.com/Suezenv/email-otp-authenticator/commit/11bf82b60263e3701e4ebcbc282c8995955fbea8))

## [1.3.0](https://github.com/Suezenv/email-otp-authenticator/compare/v1.2.2...v1.3.0) (2026-01-05)


### Features

* add conditional filters on role ([27ff32f](https://github.com/Suezenv/email-otp-authenticator/commit/27ff32f795c92f7b01be32a2f075499bfa2e863b))
* **Email OTP Authenticator:** first release ([95b68fa](https://github.com/Suezenv/email-otp-authenticator/commit/95b68fa05209e3cf48463043fb39d5e57f62157e))
* **keycloak-mfa:** add Email OTP action for enforce-mfa (netzbegruen… ([b2449c6](https://github.com/Suezenv/email-otp-authenticator/commit/b2449c6abc0b981ec313dc1c6bb841a1b50a9a2b))


### Bug Fixes

* error messages ([e7c6cf5](https://github.com/Suezenv/email-otp-authenticator/commit/e7c6cf5b132b9247faac4c7a87b115b934b068b5))
* only regenerate otp when needed ([#11](https://github.com/Suezenv/email-otp-authenticator/issues/11)) ([32bd510](https://github.com/Suezenv/email-otp-authenticator/commit/32bd5109a01a41d179e5173a8d099e3f9d0d72de))
* regenerate a new code when clicking on resend ([#13](https://github.com/Suezenv/email-otp-authenticator/issues/13)) ([2934627](https://github.com/Suezenv/email-otp-authenticator/commit/293462754bdb1b923abb60d1c723f07c619a0027))


### Documentation

* added some missing docs and auto update version in README.md ([ad850e8](https://github.com/Suezenv/email-otp-authenticator/commit/ad850e8b14522153f42aecf4c8272ccd3a3ccab2))
* fix typo in installation instructions ([a4d33c4](https://github.com/Suezenv/email-otp-authenticator/commit/a4d33c4a3be0a7f9a1073cbe5de537aa733a9842))


### Miscellaneous Chores

* add a pull request template ([af7b4d4](https://github.com/Suezenv/email-otp-authenticator/commit/af7b4d4dbc452ae9575dbf050bcff2c6051337b1))
* add UNLICENSE file ([f75339b](https://github.com/Suezenv/email-otp-authenticator/commit/f75339bf18b795f6d2c1d35ef590a5dd691fbf0c))
* **main:** release 1.0.0 ([#1](https://github.com/Suezenv/email-otp-authenticator/issues/1)) ([4333ab5](https://github.com/Suezenv/email-otp-authenticator/commit/4333ab55677974ea765bb3ae6018b29f144838f8))
* **main:** release 1.1.0 ([#4](https://github.com/Suezenv/email-otp-authenticator/issues/4)) ([89faf07](https://github.com/Suezenv/email-otp-authenticator/commit/89faf07a54d4c0caa38114c2a2358d5cd5e3b567))
* **main:** release 1.1.1 ([#6](https://github.com/Suezenv/email-otp-authenticator/issues/6)) ([b8071c3](https://github.com/Suezenv/email-otp-authenticator/commit/b8071c3182cc1b09f807ae1f79b7a7b4f71f6ea1))
* **main:** release 1.1.2 ([#7](https://github.com/Suezenv/email-otp-authenticator/issues/7)) ([0eec14a](https://github.com/Suezenv/email-otp-authenticator/commit/0eec14a8bec10bf38242873dd80b6e3ebc2328d6))
* **main:** release 1.1.3 ([#12](https://github.com/Suezenv/email-otp-authenticator/issues/12)) ([63bc347](https://github.com/Suezenv/email-otp-authenticator/commit/63bc3475fc3ece22a391ed51c787ba150d2c8784))
* **main:** release 1.1.4 ([#14](https://github.com/Suezenv/email-otp-authenticator/issues/14)) ([764bc2a](https://github.com/Suezenv/email-otp-authenticator/commit/764bc2aab090658e05179d3f43fe20093edef00c))
* **main:** release 1.2.1 ([851cd6a](https://github.com/Suezenv/email-otp-authenticator/commit/851cd6a38dfc6b2fc9903e53aab3f27e56133b56))
* **main:** release 1.2.1 ([b77dd39](https://github.com/Suezenv/email-otp-authenticator/commit/b77dd39c5fc1791091f3f9686927aeb0bb21af82))
* **main:** release 1.2.2 ([4be1021](https://github.com/Suezenv/email-otp-authenticator/commit/4be1021d4d05ce217f9e9638cc54db452ea661b0))
* update login template ([e1da760](https://github.com/Suezenv/email-otp-authenticator/commit/e1da760f98be89b4a4e33b6677c5821385dffab7))
* update translations ([86e3868](https://github.com/Suezenv/email-otp-authenticator/commit/86e38685ca32dba69b382cacb87a815b40ecaa90))


### Dependencies

* bump jakarta.ws.rs:jakarta.ws.rs-api from 3.1.0 to 4.0.0 ([#3](https://github.com/Suezenv/email-otp-authenticator/issues/3)) ([d09c727](https://github.com/Suezenv/email-otp-authenticator/commit/d09c72784d4d93cb51b918ca9d837bd89f5a2bc9))
* bump maven from 3.9.9-eclipse-temurin-21-jammy to 3-eclipse-temurin-22-jammy in /infra in the minor-versions group ([#2](https://github.com/Suezenv/email-otp-authenticator/issues/2)) ([7a6541a](https://github.com/Suezenv/email-otp-authenticator/commit/7a6541abf0e4ee670166faeea6a0422165f94e77))
* bump sigstore/cosign-installer from 3.8.1 to 3.8.2 in the minor-versions group ([#8](https://github.com/Suezenv/email-otp-authenticator/issues/8)) ([08319f4](https://github.com/Suezenv/email-otp-authenticator/commit/08319f411ad88db1c613beb6c792e8f6eacc173a))

## [1.2.1](https://github.com/for-keycloak/email-otp-authenticator/compare/main...v1.2.1)

## Features
* feat(keycloak-mfa): add Email OTP action for enforce-mfa (netzbegruen… by @wdhaoui in https://github.com/Suezenv/email-otp-authenticator/pull/1


## [1.1.4](https://github.com/for-keycloak/email-otp-authenticator/compare/v1.1.3...v1.1.4) (2025-05-05)


### Bug Fixes

* regenerate a new code when clicking on resend ([#13](https://github.com/for-keycloak/email-otp-authenticator/issues/13)) ([2934627](https://github.com/for-keycloak/email-otp-authenticator/commit/293462754bdb1b923abb60d1c723f07c619a0027))

## [1.1.3](https://github.com/for-keycloak/email-otp-authenticator/compare/v1.1.2...v1.1.3) (2025-05-02)


### Bug Fixes

* only regenerate otp when needed ([#11](https://github.com/for-keycloak/email-otp-authenticator/issues/11)) ([32bd510](https://github.com/for-keycloak/email-otp-authenticator/commit/32bd5109a01a41d179e5173a8d099e3f9d0d72de))

## [1.1.2](https://github.com/for-keycloak/email-otp-authenticator/compare/v1.1.1...v1.1.2) (2025-04-23)


### Dependencies

* bump jakarta.ws.rs:jakarta.ws.rs-api from 3.1.0 to 4.0.0 ([#3](https://github.com/for-keycloak/email-otp-authenticator/issues/3)) ([d09c727](https://github.com/for-keycloak/email-otp-authenticator/commit/d09c72784d4d93cb51b918ca9d837bd89f5a2bc9))
* bump maven from 3.9.9-eclipse-temurin-21-jammy to 3-eclipse-temurin-22-jammy in /infra in the minor-versions group ([#2](https://github.com/for-keycloak/email-otp-authenticator/issues/2)) ([7a6541a](https://github.com/for-keycloak/email-otp-authenticator/commit/7a6541abf0e4ee670166faeea6a0422165f94e77))
* bump sigstore/cosign-installer from 3.8.1 to 3.8.2 in the minor-versions group ([#8](https://github.com/for-keycloak/email-otp-authenticator/issues/8)) ([08319f4](https://github.com/for-keycloak/email-otp-authenticator/commit/08319f411ad88db1c613beb6c792e8f6eacc173a))

## [1.1.1](https://github.com/for-keycloak/email-otp-authenticator/compare/v1.1.0...v1.1.1) (2025-04-18)


### Documentation

* added some missing docs and auto update version in README.md ([ad850e8](https://github.com/for-keycloak/email-otp-authenticator/commit/ad850e8b14522153f42aecf4c8272ccd3a3ccab2))


### Miscellaneous Chores

* add a pull request template ([af7b4d4](https://github.com/for-keycloak/email-otp-authenticator/commit/af7b4d4dbc452ae9575dbf050bcff2c6051337b1))
* add UNLICENSE file ([f75339b](https://github.com/for-keycloak/email-otp-authenticator/commit/f75339bf18b795f6d2c1d35ef590a5dd691fbf0c))

## [1.1.0](https://github.com/for-keycloak/email-otp-authenticator/compare/v1.0.0...v1.1.0) (2025-04-18)


### Features

* add conditional filters on role ([27ff32f](https://github.com/for-keycloak/email-otp-authenticator/commit/27ff32f795c92f7b01be32a2f075499bfa2e863b))


### Bug Fixes

* error messages ([e7c6cf5](https://github.com/for-keycloak/email-otp-authenticator/commit/e7c6cf5b132b9247faac4c7a87b115b934b068b5))


### Documentation

* fix typo in installation instructions ([a4d33c4](https://github.com/for-keycloak/email-otp-authenticator/commit/a4d33c4a3be0a7f9a1073cbe5de537aa733a9842))


### Miscellaneous Chores

* update login template ([e1da760](https://github.com/for-keycloak/email-otp-authenticator/commit/e1da760f98be89b4a4e33b6677c5821385dffab7))
* update translations ([86e3868](https://github.com/for-keycloak/email-otp-authenticator/commit/86e38685ca32dba69b382cacb87a815b40ecaa90))

## 1.0.0 (2025-04-16)


### Features

* **Email OTP Authenticator:** first release ([95b68fa](https://github.com/for-keycloak/email-otp-authenticator/commit/95b68fa05209e3cf48463043fb39d5e57f62157e))
