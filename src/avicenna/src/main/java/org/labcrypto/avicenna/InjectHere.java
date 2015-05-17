/**

 Avicenna Dependency Injection Framework
 Copyright 2015 Kamran Amini

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.

 */

package org.labcrypto.avicenna;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used for injection points. Each field annotated by this
 * annotation, will be injected using {@link org.labcrypto.avicenna.Avicenna#inject(Object...)}
 * method.
 *
 * @author Kamran Amini  kam.cpp@gmail.com
 * @see org.labcrypto.avicenna.Avicenna
 * @see org.labcrypto.avicenna.Avicenna#addDependencyFactory(Object...)
 * @see org.labcrypto.avicenna.Avicenna#inject(Object...)
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectHere {
}
