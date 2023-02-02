import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Partenaire from './partenaire';
import PartenaireDetail from './partenaire-detail';
import PartenaireUpdate from './partenaire-update';
import PartenaireDeleteDialog from './partenaire-delete-dialog';

const PartenaireRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Partenaire />} />
    <Route path="new" element={<PartenaireUpdate />} />
    <Route path=":id">
      <Route index element={<PartenaireDetail />} />
      <Route path="edit" element={<PartenaireUpdate />} />
      <Route path="delete" element={<PartenaireDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PartenaireRoutes;
