# @veelit/ussd-manager

The plugin allows the use of several ussd features on Android

## Install

```bash
npm install @veelit/ussd-manager
npx cap sync
```

## API

<docgen-index>

* [`echo(...)`](#echo)
* [`requestUssdPermission()`](#requestussdpermission)
* [`callUssd(...)`](#callussd)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### echo(...)

```typescript
echo(options: { value: string; }) => Promise<{ value: string; }>
```

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ value: string; }</code> |

**Returns:** <code>Promise&lt;{ value: string; }&gt;</code>

--------------------


### requestUssdPermission()

```typescript
requestUssdPermission() => Promise<void>
```

--------------------


### callUssd(...)

```typescript
callUssd(options: { value: string; }) => Promise<{ result: string; code: string; }>
```

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ value: string; }</code> |

**Returns:** <code>Promise&lt;{ result: string; code: string; }&gt;</code>

--------------------

</docgen-api>
