import { registerPlugin } from '@capacitor/core';

import type { UssdManagerPlugin } from './definitions';

const UssdManager = registerPlugin<UssdManagerPlugin>('UssdManager', {
  web: () => import('./web').then(m => new m.UssdManagerWeb()),
});

export * from './definitions';
export { UssdManager };
