package org.eclipse.emf.compare.uml2.tests.association;

import static com.google.common.base.Predicates.and;
import static com.google.common.base.Predicates.instanceOf;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertSame;
import static junit.framework.Assert.assertTrue;
import static org.eclipse.emf.compare.utils.EMFComparePredicates.addedToReference;
import static org.eclipse.emf.compare.utils.EMFComparePredicates.changedReference;
import static org.eclipse.emf.compare.utils.EMFComparePredicates.ofKind;
import static org.eclipse.emf.compare.utils.EMFComparePredicates.onEObject;
import static org.eclipse.emf.compare.utils.EMFComparePredicates.onFeature;
import static org.eclipse.emf.compare.utils.EMFComparePredicates.removedFromReference;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;

import java.io.IOException;
import java.util.List;

import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.DifferenceKind;
import org.eclipse.emf.compare.EMFCompare;
import org.eclipse.emf.compare.scope.IComparisonScope;
import org.eclipse.emf.compare.uml2.AssociationChange;
import org.eclipse.emf.compare.uml2.tests.AbstractTest;
import org.eclipse.emf.compare.uml2.tests.association.data.AssociationInputData;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Test;

@SuppressWarnings("nls")
public class ChangeAssociationTest extends AbstractTest {

	private AssociationInputData input = new AssociationInputData();

	@Test
	public void testA20UseCase() throws IOException {
		final Resource left = input.getA2Left();
		final Resource right = input.getA2Right();

		final IComparisonScope scope = EMFCompare.createDefaultScope(left, right);
		final Comparison comparison = EMFCompare.newComparator(scope).compare();
		testAB1(TestKind.ADD, comparison);
	}

	@Test
	public void testA21UseCase() throws IOException {
		final Resource left = input.getA2Left();
		final Resource right = input.getA2Right();

		final IComparisonScope scope = EMFCompare.createDefaultScope(right, left);
		final Comparison comparison = EMFCompare.newComparator(scope).compare();
		testAB1(TestKind.DELETE, comparison);
	}

	private void testAB1(TestKind kind, final Comparison comparison) {
		final List<Diff> differences = comparison.getDifferences();

		// We should have no less and no more than 7 differences
		assertSame(Integer.valueOf(7), Integer.valueOf(differences.size()));

		Predicate<? super Diff> addMemberEndClass2InAssociationDescription = null;
		Predicate<? super Diff> addPropertyClass2Description = null;
		Predicate<? super Diff> addRefAssociationInPropertyClass2Description = null;
		Predicate<? super Diff> addRefTypeInPropertyClass2Description = null;
		Predicate<? super Diff> addLiteralIntegerInClass2Description = null;
		Predicate<? super Diff> addUnlimitedNaturalInClass2Description = null;

		if (kind.equals(TestKind.DELETE)) {
			addMemberEndClass2InAssociationDescription = removedFromReference("model.class1_class0_0",
					"memberEnd", "model.class1_class0_0.Class2");
			addPropertyClass2Description = removedFromReference("model.class1_class0_0", "ownedEnd",
					"model.class1_class0_0.Class2");
			addRefAssociationInPropertyClass2Description = changedReference("model.class1_class0_0.Class2",
					"association", "model.class1_class0_0", null);
			addRefTypeInPropertyClass2Description = changedReference("model.class1_class0_0.Class2", "type",
					"model.Class2", null);
			addLiteralIntegerInClass2Description = removedLowerValueIn("model.class1_class0_0.Class2");
			addUnlimitedNaturalInClass2Description = removedUpperValueIn("model.class1_class0_0.Class2");
		} else {
			addMemberEndClass2InAssociationDescription = addedToReference("model.class1_class0_0",
					"memberEnd", "model.class1_class0_0.Class2");
			addPropertyClass2Description = addedToReference("model.class1_class0_0", "ownedEnd",
					"model.class1_class0_0.Class2");
			addRefAssociationInPropertyClass2Description = changedReference("model.class1_class0_0.Class2",
					"association", null, "model.class1_class0_0");
			addRefTypeInPropertyClass2Description = changedReference("model.class1_class0_0.Class2", "type",
					null, "model.Class2");
			addLiteralIntegerInClass2Description = addedLowerValueIn("model.class1_class0_0.Class2");
			addUnlimitedNaturalInClass2Description = addedUpperValueIn("model.class1_class0_0.Class2");
		}

		final Diff addMemberEndClass2InAssociation = Iterators.find(differences.iterator(),
				addMemberEndClass2InAssociationDescription);
		final Diff addPropertyClass2 = Iterators.find(differences.iterator(), addPropertyClass2Description);
		final Diff addRefAssociationInPropertyClass2 = Iterators.find(differences.iterator(),
				addRefAssociationInPropertyClass2Description);
		final Diff addRefTypeInPropertyClass2 = Iterators.find(differences.iterator(),
				addRefTypeInPropertyClass2Description);
		final Diff addLiteralIntegerInClass2 = Iterators.find(differences.iterator(),
				addLiteralIntegerInClass2Description);
		final Diff addUnlimitedNaturalInClass2 = Iterators.find(differences.iterator(),
				addUnlimitedNaturalInClass2Description);

		assertNotNull(addMemberEndClass2InAssociation);
		assertNotNull(addPropertyClass2);
		assertNotNull(addRefAssociationInPropertyClass2);
		assertNotNull(addRefTypeInPropertyClass2);
		assertNotNull(addLiteralIntegerInClass2);
		assertNotNull(addUnlimitedNaturalInClass2);

		// CHECK EXTENSION
		assertSame(Integer.valueOf(1), count(differences, instanceOf(AssociationChange.class)));
		Diff changeUMLAssociation = Iterators.find(differences.iterator(), and(
				instanceOf(AssociationChange.class), ofKind(DifferenceKind.CHANGE)));
		assertNotNull(changeUMLAssociation);
		assertSame(Integer.valueOf(4), Integer.valueOf(changeUMLAssociation.getRefinedBy().size()));
		assertTrue(changeUMLAssociation.getRefinedBy().contains(addRefTypeInPropertyClass2));
		assertTrue(changeUMLAssociation.getRefinedBy().contains(addLiteralIntegerInClass2));
		assertTrue(changeUMLAssociation.getRefinedBy().contains(addUnlimitedNaturalInClass2));
		assertTrue(changeUMLAssociation.getRefinedBy().contains(addRefAssociationInPropertyClass2));

		// CHECK REQUIREMENT
		if (kind.equals(TestKind.ADD)) {
			assertSame(Integer.valueOf(0), Integer.valueOf(addPropertyClass2.getRequires().size()));

			assertSame(Integer.valueOf(1), Integer.valueOf(addRefAssociationInPropertyClass2.getRequires()
					.size()));
			assertTrue(addRefAssociationInPropertyClass2.getRequires().contains(addPropertyClass2));

			assertSame(Integer.valueOf(1), Integer.valueOf(addRefTypeInPropertyClass2.getRequires().size()));
			assertTrue(addRefTypeInPropertyClass2.getRequires().contains(addPropertyClass2));

			assertSame(Integer.valueOf(0), Integer.valueOf(changeUMLAssociation.getRequires().size()));

			assertSame(Integer.valueOf(1), Integer.valueOf(addMemberEndClass2InAssociation.getRequires()
					.size()));
			assertTrue(addMemberEndClass2InAssociation.getRequires().contains(addPropertyClass2));

			assertSame(Integer.valueOf(1), Integer.valueOf(addLiteralIntegerInClass2.getRequires().size()));
			assertTrue(addLiteralIntegerInClass2.getRequires().contains(addPropertyClass2));

			assertSame(Integer.valueOf(1), Integer.valueOf(addUnlimitedNaturalInClass2.getRequires().size()));
			assertTrue(addUnlimitedNaturalInClass2.getRequires().contains(addPropertyClass2));
		} else {
			assertSame(Integer.valueOf(5), Integer.valueOf(addPropertyClass2.getRequires().size()));
			assertTrue(addPropertyClass2.getRequires().contains(addLiteralIntegerInClass2));
			assertTrue(addPropertyClass2.getRequires().contains(addUnlimitedNaturalInClass2));
			assertTrue(addPropertyClass2.getRequires().contains(addRefAssociationInPropertyClass2));
			assertTrue(addPropertyClass2.getRequires().contains(addRefTypeInPropertyClass2));
			assertTrue(addPropertyClass2.getRequires().contains(addMemberEndClass2InAssociation));

			assertSame(Integer.valueOf(0), Integer.valueOf(addRefAssociationInPropertyClass2.getRequires()
					.size()));
			assertSame(Integer.valueOf(0), Integer.valueOf(addRefTypeInPropertyClass2.getRequires().size()));
			assertSame(Integer.valueOf(0), Integer.valueOf(changeUMLAssociation.getRequires().size()));
			assertSame(Integer.valueOf(0), Integer.valueOf(addMemberEndClass2InAssociation.getRequires()
					.size()));
			assertSame(Integer.valueOf(0), Integer.valueOf(addLiteralIntegerInClass2.getRequires().size()));
			assertSame(Integer.valueOf(0), Integer.valueOf(addUnlimitedNaturalInClass2.getRequires().size()));
		}

		// CHECK EQUIVALENCE
		assertSame(Integer.valueOf(1), Integer.valueOf(comparison.getEquivalences().size()));

		assertNotNull(addMemberEndClass2InAssociation.getEquivalence());
		assertSame(Integer.valueOf(2), Integer.valueOf(addMemberEndClass2InAssociation.getEquivalence()
				.getDifferences().size()));
		assertTrue(addMemberEndClass2InAssociation.getEquivalence().getDifferences().contains(
				addMemberEndClass2InAssociation));
		assertTrue(addMemberEndClass2InAssociation.getEquivalence().getDifferences().contains(
				addRefAssociationInPropertyClass2));

	}

	private static Predicate<? super Diff> addedLowerValueIn(final String qualifiedName) {
		return and(ofKind(DifferenceKind.ADD), onEObject(qualifiedName), onFeature("lowerValue"));
	}

	private static Predicate<? super Diff> addedUpperValueIn(final String qualifiedName) {
		return and(ofKind(DifferenceKind.ADD), onEObject(qualifiedName), onFeature("upperValue"));
	}

	private static Predicate<? super Diff> removedLowerValueIn(final String qualifiedName) {
		return and(ofKind(DifferenceKind.DELETE), onEObject(qualifiedName), onFeature("lowerValue"));
	}

	private static Predicate<? super Diff> removedUpperValueIn(final String qualifiedName) {
		return and(ofKind(DifferenceKind.DELETE), onEObject(qualifiedName), onFeature("upperValue"));
	}

}
