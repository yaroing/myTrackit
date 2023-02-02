import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import PointFocalPartenaire from './point-focal-partenaire';
import PointFocalPartenaireDetail from './point-focal-partenaire-detail';
import PointFocalPartenaireUpdate from './point-focal-partenaire-update';
import PointFocalPartenaireDeleteDialog from './point-focal-partenaire-delete-dialog';

const PointFocalPartenaireRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<PointFocalPartenaire />} />
    <Route path="new" element={<PointFocalPartenaireUpdate />} />
    <Route path=":id">
      <Route index element={<PointFocalPartenaireDetail />} />
      <Route path="edit" element={<PointFocalPartenaireUpdate />} />
      <Route path="delete" element={<PointFocalPartenaireDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PointFocalPartenaireRoutes;
