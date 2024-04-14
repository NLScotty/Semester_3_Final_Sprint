require('dotenv').config();
global.DEBUG = false;

const grantController = require('../controller/grant-controllers');

test('retreives grants from sql database', async () => {
    let result = await grantController.getGrants('sql')
    expect(result).not.toBe([]);
})

test('retreives grants from mongo database', async () => {
    let result = await grantController.getGrants('mongo')
    expect(result).not.toBe([]);
})

test('Supplier Contains Test', async () => {
    let result = await grantController.supplierContains('sql','Scott')
    expect(result).not.toBe([]);
})

test('Supplier Exact Match Test - Test to fail', async () => {
    let result = await grantController.exactMatch('sql','Scott')
    expect(result).toStrictEqual([]);
})

test('Supplier Exact Match Test - Test to pass', async () => {
    let result = await grantController.exactMatch('sql','GAMING ADDICTION THERAPY FOR SCOTT NORMORE')
    expect(result).toStrictEqual([{"grant_id":69,"supplier_name":"GAMING ADDICTION THERAPY FOR SCOTT NORMORE","amount":"1365723"}]);
})

test('Ammount = to PASS', async () => {
    let result = await grantController.amountEqual('sql','1365723')
    expect(result).toStrictEqual([{"grant_id":69,"supplier_name":"GAMING ADDICTION THERAPY FOR SCOTT NORMORE","amount":"1365723"}]);
})

test('Ammount = to FAIL', async () => {
    let result = await grantController.amountEqual('sql','12')
    expect(result).toStrictEqual([]);
})

test('Amount > and < ', async () => {
    let result1 = await grantController.amountHigher('sql','1365723')
    let result2 = await grantController.amountLower('sql','1365723')
    expect(result1).not.toBe([]);
    expect(result2).not.toBe([]);
    expect(result1).not.toStrictEqual(result2);
})
