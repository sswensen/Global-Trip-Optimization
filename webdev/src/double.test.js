import double from './double.js';

describe("Double", () => {
    it("Doubles two to four", () => {
        expect(double(2)).toEqual(4);
    });

    it("Doubles zero to zero", () => {
        expect(double(0)).toEqual(0);
    });
});