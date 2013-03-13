/*******************************************************************************
 * Copyright (c) 2011, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.compare.uml2.internal.postprocessor.extension.profile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.compare.AttributeChange;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.DifferenceKind;
import org.eclipse.emf.compare.Match;
import org.eclipse.emf.compare.ReferenceChange;
import org.eclipse.emf.compare.ResourceAttachmentChange;
import org.eclipse.emf.compare.uml2.internal.ProfileApplicationChange;
import org.eclipse.emf.compare.uml2.internal.StereotypeApplicationChange;
import org.eclipse.emf.compare.uml2.internal.UMLCompareFactory;
import org.eclipse.emf.compare.uml2.internal.UMLDiff;
import org.eclipse.emf.compare.uml2.internal.postprocessor.extension.AbstractDiffExtensionFactory;
import org.eclipse.emf.compare.uml2.internal.postprocessor.util.UMLCompareUtil;
import org.eclipse.emf.compare.utils.MatchUtil;
import org.eclipse.emf.compare.utils.ReferenceUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.uml2.common.util.UML2Util;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.ProfileApplication;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 * Factory for UMLStereotypeApplicationRemoval.
 * 
 * @author <a href="mailto:cedric.notot@obeo.fr">Cedric Notot</a>
 */
public class UMLStereotypeApplicationChangeFactory extends AbstractDiffExtensionFactory {

	public Class<? extends UMLDiff> getExtensionKind() {
		return StereotypeApplicationChange.class;
	}

	@Override
	protected UMLDiff createExtension() {
		return UMLCompareFactory.eINSTANCE.createStereotypeApplicationChange();
	}

	@Override
	protected EObject getDiscriminantFromDiff(Diff input) {
		EObject result = null;
		final DifferenceKind kind = getRelatedExtensionKind(input);
		if (kind == DifferenceKind.ADD || kind == DifferenceKind.DELETE) {
			if (input instanceof ReferenceChange) {
				return ((ReferenceChange)input).getValue();
			} else if (input instanceof ResourceAttachmentChange) {
				return MatchUtil.getContainer(input.getMatch().getComparison(), input);
			}
		} else if (kind == DifferenceKind.CHANGE) {
			return MatchUtil.getContainer(input.getMatch().getComparison(), input);
		}
		return result;
	}

	@Override
	protected void fillRefiningDifferences(Comparison comparison, UMLDiff diffExtension, EObject discriminant) {
		super.fillRefiningDifferences(comparison, diffExtension, discriminant);

		final Iterator<Diff> changes = comparison.getMatch(discriminant).getDifferences().iterator();
		while (changes.hasNext()) {
			final Diff diff = changes.next();
			if (diff instanceof AttributeChange || diff instanceof ResourceAttachmentChange
					&& diff.getKind() == DifferenceKind.DELETE) {
				diffExtension.getRefinedBy().add(diff);
			}
		}
	}

	@Override
	protected List<EObject> getPotentialChangedValuesFromDiscriminant(EObject discriminant) {
		// get the changed values linked to the related stereotype.
		final List<EObject> result = new ArrayList<EObject>();
		final Iterator<EReference> features = discriminant.eClass().getEAllReferences().iterator();
		while (features.hasNext()) {
			final EStructuralFeature feature = features.next();
			if (feature.isMany()) {
				result.addAll((List<? extends EObject>)ReferenceUtil.getAsList(discriminant, feature));
			} else {
				if (((InternalEObject)discriminant).eGet(feature, false) != null) {
					result.add((EObject)((InternalEObject)discriminant).eGet(feature, false));
				}
			}
		}
		return result;
	}

	@Override
	protected boolean isRelatedToAnExtensionChange(AttributeChange input) {
		return UMLCompareUtil.getBaseElement(MatchUtil.getContainer(input.getMatch().getComparison(), input)) != null;
	}

	@Override
	protected boolean isRelatedToAnExtensionChange(ReferenceChange input) {
		return UMLCompareUtil.getBaseElement(MatchUtil.getContainer(input.getMatch().getComparison(), input)) != null;
	}

	@Override
	protected boolean isRelatedToAnExtensionAdd(ResourceAttachmentChange input) {
		return input.getKind() == DifferenceKind.ADD
				&& UMLCompareUtil.getBaseElement(MatchUtil.getContainer(input.getMatch().getComparison(),
						input)) != null;
	}

	@Override
	protected boolean isRelatedToAnExtensionDelete(ResourceAttachmentChange input) {
		return input.getKind() == DifferenceKind.DELETE
				&& UMLCompareUtil.getBaseElement(MatchUtil.getContainer(input.getMatch().getComparison(),
						input)) != null;
	}

	@Override
	public void fillRequiredDifferences(Comparison comparison, UMLDiff extension) {
		if (!(extension instanceof StereotypeApplicationChange)) {
			return;
		}
		final StereotypeApplicationChange stereotypeApplicationChange = (StereotypeApplicationChange)extension;
		if (stereotypeApplicationChange.getKind() == DifferenceKind.ADD) {
			final Stereotype stereotype = UMLUtil
					.getStereotype(stereotypeApplicationChange.getDiscriminant());
			if (stereotype != null) {
				// FIXME this is not the place to set this, it should be done at creation time.
				stereotypeApplicationChange.setStereotype(stereotype);
				final Profile profile = stereotype.getProfile();
				final Iterator<Setting> settings = UML2Util.getInverseReferences(profile).iterator();
				while (settings.hasNext()) {
					final Setting setting = settings.next();
					if (setting.getEStructuralFeature() == UMLPackage.Literals.PROFILE_APPLICATION__APPLIED_PROFILE) {
						final ProfileApplication profileApplication = (ProfileApplication)setting
								.getEObject();
						for (Diff diff : comparison.getDifferences(profileApplication)) {
							if (diff instanceof ProfileApplicationChange
									&& diff.getKind() == DifferenceKind.ADD) {
								stereotypeApplicationChange.getRequires().add(diff);
							}
						}
					}
				}
			}
		}
	}

	@Override
	public Match getParentMatch(Diff input) {
		final EObject discriminant = getDiscriminantFromDiff(input);
		if (discriminant != null) {
			final Element element = UMLCompareUtil.getBaseElement(discriminant);
			final Match match = input.getMatch().getComparison().getMatch(element);
			if (match != null) {
				return match;
			}
		}
		return super.getParentMatch(input);
	}

}