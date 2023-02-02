import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import PointService from './point-service';
import PointServiceDetail from './point-service-detail';
import PointServiceUpdate from './point-service-update';
import PointServiceDeleteDialog from './point-service-delete-dialog';

const PointServiceRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<PointService />} />
    <Route path="new" element={<PointServiceUpdate />} />
    <Route path=":id">
      <Route index element={<PointServiceDetail />} />
      <Route path="edit" element={<PointServiceUpdate />} />
      <Route path="delete" element={<PointServiceDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PointServiceRoutes;
