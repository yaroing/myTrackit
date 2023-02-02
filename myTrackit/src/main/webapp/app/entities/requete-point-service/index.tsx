import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import RequetePointService from './requete-point-service';
import RequetePointServiceDetail from './requete-point-service-detail';
import RequetePointServiceUpdate from './requete-point-service-update';
import RequetePointServiceDeleteDialog from './requete-point-service-delete-dialog';

const RequetePointServiceRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<RequetePointService />} />
    <Route path="new" element={<RequetePointServiceUpdate />} />
    <Route path=":id">
      <Route index element={<RequetePointServiceDetail />} />
      <Route path="edit" element={<RequetePointServiceUpdate />} />
      <Route path="delete" element={<RequetePointServiceDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RequetePointServiceRoutes;
