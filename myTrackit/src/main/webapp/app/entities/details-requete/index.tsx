import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import DetailsRequete from './details-requete';
import DetailsRequeteDetail from './details-requete-detail';
import DetailsRequeteUpdate from './details-requete-update';
import DetailsRequeteDeleteDialog from './details-requete-delete-dialog';

const DetailsRequeteRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<DetailsRequete />} />
    <Route path="new" element={<DetailsRequeteUpdate />} />
    <Route path=":id">
      <Route index element={<DetailsRequeteDetail />} />
      <Route path="edit" element={<DetailsRequeteUpdate />} />
      <Route path="delete" element={<DetailsRequeteDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DetailsRequeteRoutes;
