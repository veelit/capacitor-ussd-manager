import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(UssdManagerPlugin)
public class UssdManagerPlugin: CAPPlugin {
    private let implementation = UssdManager()

    @objc func echo(_ call: CAPPluginCall) {
        let value = call.getString("value") ?? ""
        call.resolve([
            "value": implementation.echo(value)
        ])
    }    

    @objc func requestUssdPermission(_ call: CAPPluginCall) {
        call.resolve()
    }

    @objc func callUssd(_ call: CAPPluginCall) {
        let value = call.getString("value") ?? ""

        call.resolve([
            "result": "This plugin is not supported on ios",
            "code": value,
        ])
    }
}
