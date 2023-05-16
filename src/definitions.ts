export interface UssdManagerPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
  requestUssdPermission(): Promise<void>;
  callUssd(options: {
    value: string;
  }): Promise<{ result: string; code: string }>;
}
