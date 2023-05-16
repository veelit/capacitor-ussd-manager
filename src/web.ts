import { WebPlugin } from '@capacitor/core';

import type { UssdManagerPlugin } from './definitions';

export class UssdManagerWeb extends WebPlugin implements UssdManagerPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }

  async requestUssdPermission(): Promise<void> {
    return;
  }

  async callUssd(options: {
    value: string;
  }): Promise<{ result: string; code: string }> {
    return {
      result: 'This plugin is not supported on the web',
      code: options.value,
    };
  }
}
