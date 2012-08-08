/**
 * Copyright (c) 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 */
package org.eclipse.emf.compare.uml2;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.emf.compare.uml2.UMLComparePackage
 * @generated
 */
public interface UMLCompareFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	UMLCompareFactory eINSTANCE = org.eclipse.emf.compare.uml2.impl.UMLCompareFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Association Change</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Association Change</em>'.
	 * @generated
	 */
	AssociationChange createAssociationChange();

	/**
	 * Returns a new object of class '<em>Dependency Change</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Dependency Change</em>'.
	 * @generated
	 */
	DependencyChange createDependencyChange();

	/**
	 * Returns a new object of class '<em>Interface Realization Change</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Interface Realization Change</em>'.
	 * @generated
	 */
	InterfaceRealizationChange createInterfaceRealizationChange();

	/**
	 * Returns a new object of class '<em>Substitution Change</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Substitution Change</em>'.
	 * @generated
	 */
	SubstitutionChange createSubstitutionChange();

	/**
	 * Returns a new object of class '<em>Extend Change</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Extend Change</em>'.
	 * @generated
	 */
	ExtendChange createExtendChange();

	/**
	 * Returns a new object of class '<em>Include Change</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Include Change</em>'.
	 * @generated
	 */
	IncludeChange createIncludeChange();

	/**
	 * Returns a new object of class '<em>Generalization Set Change</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Generalization Set Change</em>'.
	 * @generated
	 */
	GeneralizationSetChange createGeneralizationSetChange();

	/**
	 * Returns a new object of class '<em>Execution Specification Change</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Execution Specification Change</em>'.
	 * @generated
	 */
	ExecutionSpecificationChange createExecutionSpecificationChange();

	/**
	 * Returns a new object of class '<em>Interval Constraint Change</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Interval Constraint Change</em>'.
	 * @generated
	 */
	IntervalConstraintChange createIntervalConstraintChange();

	/**
	 * Returns a new object of class '<em>Message Change</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Message Change</em>'.
	 * @generated
	 */
	MessageChange createMessageChange();

	/**
	 * Returns a new object of class '<em>Stereotype Property Change</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Stereotype Property Change</em>'.
	 * @generated
	 */
	StereotypePropertyChange createStereotypePropertyChange();

	/**
	 * Returns a new object of class '<em>Stereotype Application Change</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Stereotype Application Change</em>'.
	 * @generated
	 */
	StereotypeApplicationChange createStereotypeApplicationChange();

	/**
	 * Returns a new object of class '<em>Stereotype Reference Change</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Stereotype Reference Change</em>'.
	 * @generated
	 */
	StereotypeReferenceChange createStereotypeReferenceChange();

	/**
	 * Returns a new object of class '<em>Profile Application Change</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Profile Application Change</em>'.
	 * @generated
	 */
	ProfileApplicationChange createProfileApplicationChange();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	UMLComparePackage getUMLComparePackage();

} //UMLCompareFactory
