import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Zrosts from './zrosts';
import ZrostsDetail from './zrosts-detail';
import ZrostsUpdate from './zrosts-update';
import ZrostsDeleteDialog from './zrosts-delete-dialog';

const ZrostsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Zrosts />} />
    <Route path="new" element={<ZrostsUpdate />} />
    <Route path=":id">
      <Route index element={<ZrostsDetail />} />
      <Route path="edit" element={<ZrostsUpdate />} />
      <Route path="delete" element={<ZrostsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ZrostsRoutes;
