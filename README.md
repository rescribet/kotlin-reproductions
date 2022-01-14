# Kotlin Reproductions

When using a generic non-top level class with a class which resolves to `any`
the Kotlin compiler will output nested block comments, which cause the TS 
compiler to print `TS1005: ',' expected.` errors.

The TypeScript team has indicated they [won't support nested block comment 
parsing](https://github.com/microsoft/TypeScript/issues/40865#issuecomment-702200915)

## Expected
Something to ensure the closing block comment doesn't close a previous one.

```typescript
export class Foo {
    constructor(baz: any/* kotlin.collections.List<any/* Bar *\/> */);
    get baz(): any/* kotlin.collections.List<any/* Bar *\/> */;
    component1(): any/* kotlin.collections.List<any/* Bar *\/> */;
    copy(baz: any/* kotlin.collections.List<any/* Bar *\/> */): Foo;
    toString(): string;
    hashCode(): number;
    equals(other: Nullable<any>): boolean;
}
```

## Actual

```typescript
export class Foo {
    constructor(baz: any/* kotlin.collections.List<any/* Bar */> */);
    get baz(): any/* kotlin.collections.List<any/* Bar */> */;
    component1(): any/* kotlin.collections.List<any/* Bar */> */;
    copy(baz: any/* kotlin.collections.List<any/* Bar */> */): Foo;
    toString(): string;
    hashCode(): number;
    equals(other: Nullable<any>): boolean;
}
```
