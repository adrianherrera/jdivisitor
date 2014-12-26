JDIVisitor
==========

JDIVisitor is a Java library for building custom debugger applications using the Java Debug Interface (JDI) and the visitor design pattern. It draws inspiration from [jfager](https://github.com/jfager)'s [jdiscript](https://github.com/jfager/jdiscript) project. However, JDIVisitor eschews jdiscript's script-like design in favour of a more Java-esque library. An advantage of using the visitor pattern is that the visitor object can encapsulate and maintain state as it handles JDI events, allowing for complex debugger applications to be constructed.

## License
GNU GPL v2.0

## Usage
The following steps can be used to create your custom debugger application:

1. Implement an `EventRequestor` class that requests specific JDI events that will be handled by your debugger
2. Implement an `EventVisitor` class that describes how to handle the JDI events enabled in Step 1
3. Connect to the target virtual machine using either the `LocalVMLauncher` class (creates and starts a local virtual machine in debug mode) or the `RemoteVMConnector` class (attach to a virtual machine already running in debug mode over a network). Note that using the `RemoteVMConnector` and an appropriately-configured sandbox is the safer choice if you are working with malicious Java applications
4. Instantiate a ``Debugger`` object using the `VirtualMachine` object returned from Step 3
5. Call the debugger's `requestEvents` method with the `EventRequestor` implemented in Step 1
6. Call the debugger's `run` method with the `EventVisitor` implemented in Step 2

## Examples
See https://github.com/adrianherrera/jdivisitor-examples.
