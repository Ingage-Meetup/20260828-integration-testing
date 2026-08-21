/**
 * @typedef {Object} User
 * @property {number} id
 * @property {string} name
 * @property {string} username
 * @property {string} email
 * @property {string} phone
 * @property {string} website
 * @property {Object} company
 */

/**
 * @typedef {Object} Company
 * @property {string} name
 * @property {string} catchPhrase
 * @property {string} bs
 */

/**
 * @typedef {Object} Address
 * @property {string} street
 * @property {string} suite
 * @property {string} city
 * @property {string} zipecode
 * @property {Geo} geo
 */

/**
 * @typedef {Object} Geo
 * @property {string} lat
 * @property {string} lng
 */

/**
 * @typedef {User & {address: Address}} AddressUser
 */

module.exports = {}
