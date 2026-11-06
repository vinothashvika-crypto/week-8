# Week 8 – Quiz and Concept Question Answers

Topic: inheritance and polymorphism.

## Quiz Questions

| Q | Answer | Why |
|---|--------|-----|
| 1 | **C** – Polymorphism | Each document type overrides `render()`, so callers just call `doc.render()` with no `if-else` on the type. |
| 2 | **A, B, C** | A car *is a* vehicle, a rectangle *is a* shape, and a database connection *is a* network resource. D is a *has-a* (composition) relationship. |
| 3 | **C** – Runtime method dispatch | The JVM picks the override based on the actual object type at run time. |
| 4 | **C** – Inheritance-based polymorphism | One `calculateArea()` call behaves differently for each subclass. |
| 5 | **A, C, D** | Book extends the inherited behavior, the base class defines the common behavior, and a DVD can be used through a `LibraryItem` reference. B is wrong: DVD *overrides* the method (specialized behavior), it doesn't share it. |
| 6 | **A, C** | An `Animal` reference to a `Dog` runs `Dog.makeSound()`, and `Dog.makeSound()` is an override. B is wrong (the base version is the general one), and D is wrong (subclasses can add brand-new methods). |
| 7 | **C** | A new `WalletPayment` just needs its own `calculateFee()`; the processor loop doesn't change. |
| 8 | **A** | Inheriting only to reuse code creates false *is-a* links and tight coupling. |
| 9 | **A, C** | The sender works with any notification type, and new channels can be added without touching it. B and D describe the opposite of what overriding does. |
| 10 | **A, B, C** | Polymorphic collections make adding new types *easier*, not harder, so D is wrong. |

## Concept Questions

**1. Reuse and extend behavior through inheritance.**
A subclass automatically gets the base class's fields and methods (reuse), can override methods to change them, and can add new methods (extend). Example: `BankAccount` has `deposit()` and `getBalance()`. `SavingsAccount extends BankAccount` reuses both, adds `addInterest()`, and overrides `withdraw()` to block withdrawals below a minimum balance.

**2. The *is-a* relationship.**
Inheritance should model "B is a kind of A", meaning every B can be used anywhere an A is expected (the Liskov substitution idea). Getting this right matters because subclasses inherit *all* of the parent's behavior. If the relationship is false (for example `Stack extends ArrayList`), the subclass exposes operations that make no sense for it and breaks the expectations of code that uses the parent type.

**3. Method overriding.**
Overriding means a subclass gives its own implementation of a method with the same signature as one in its parent. Example: `Employee` declares `calculateSalary()`. `FullTimeEmployee` returns the monthly salary plus benefits, `PartTimeEmployee` returns hours × hourly rate, and `Contractor` returns the contract amount. Payroll code calls `e.calculateSalary()` on every employee and gets the right result for each one.

**4. Runtime polymorphism (dynamic dispatch).**
The *reference* type (say `Payment p`) decides which methods you are allowed to call at compile time. The *actual object* (`new CardPayment()`) decides which implementation runs. At run time the JVM looks up the method in the object's class (through its method table). If that class overrides the method, that version runs; otherwise it walks up to the parent's version.

**5. Polymorphic collections.**
A `List<Shape>` can hold `Circle`, `Rectangle` and `Triangle` objects. One loop like `for (Shape s : shapes) total += s.area();` works for all of them. The advantages are simpler loops, no casting, no type checks, and new shapes that work with the existing loop automatically.

**6. Polymorphism vs. type-based `if-else` or `switch`.**
With conditionals, every place that needs type-specific behavior repeats a chain like `if (type == CARD) ... else if (type == WALLET) ...`. Adding a type means finding and editing every one of those chains, and a missed one causes a bug. With polymorphism, each type's behavior lives in one class, callers stay simple, and adding a type means adding one class (the open/closed principle).

**7. Adding a new type with minimal changes.**
The `PaymentProcessor` loops over `List<Payment>` and calls `calculateFee()`. To support UPI, write `class UpiPayment extends Payment { double calculateFee() { return 0; } }` and register it where objects are created. The processor's loop and totals don't change at all.

**8. Inherited vs. overridden behavior.**
*Inherited* means the subclass uses the parent's method as-is. That suits behavior that really is the same for all subtypes, such as `getName()` on every `Employee`. *Overridden* means the subclass replaces it, which suits behavior that differs by type, such as `calculateSalary()` or `getLoanPeriod()`. A subclass can also call `super.method()` inside its override to extend the parent's behavior instead of replacing it entirely.

**9. Inheritance just for code reuse.**
If `Car extends Engine` only to reuse `start()`, a car now claims to *be* an engine, gets every other engine method, and is tied to `Engine`'s internals. Changes to `Engine` can break `Car`, and a `Car` can be passed anywhere an `Engine` is expected. The usual fix is composition: the car *has an* engine field.

**10. VehicleRental with CarRental and TruckRental.**
*Common* behavior (rental ID, customer, start and end dates, `getDays()`, a base `calculateCost()` of days × daily rate, and printing an invoice) goes in an abstract `Rental` class. *Specialized* behavior goes in subclasses. `CarRental` adds a per-day insurance option. `TruckRental` adds a per-km charge and a load-weight surcharge, so it overrides `calculateCost()`. Billing code works with `Rental` references only.
